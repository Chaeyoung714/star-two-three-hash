package miniproject.star_two_three.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.dto.jwt.TokenResponseDTO;
import miniproject.star_two_three.exception.CustomException;
import miniproject.star_two_three.exception.Exceptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${spring.application.name}")
    private String issuer;
    @Value("${spring.jwt.access-expiration}")
    private Long accessExpiration;
    @Value("${spring.jwt.refresh-expiration}")
    private Long refreshExpiration;
    @Value("${spring.jwt.secret-key}")
    private String key;
    private SecretKey secretKey;
    private final MemoryBlackList blackList;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }


    /*액세스 토큰 발급*/
    public String createToken(Long memberId, TokenType tokenType) {
        Date expiration;
        Date now = new Date();
        if (tokenType == TokenType.ACCESS) {
            expiration = new Date(now.getTime() + accessExpiration);
        } else {
            expiration = new Date(now.getTime() + refreshExpiration);
        }

        return Jwts.builder()
                .header().type("JWT").and()
                .id(UUID.randomUUID().toString())
                .issuer(issuer)
                .subject(memberId.toString())
                .claim("type", tokenType.name())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }


    /*리프레시 토큰 검증 후 새로운 액세스 토큰 발급*/
    public TokenResponseDTO refreshAccessToken(String refreshToken) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(refreshToken).getPayload();
        if (claims.getExpiration().before(new Date())) {
            throw new CustomException(Exceptions.EXPIRED_TOKEN);
        }
        if (!claims.get("type").equals(TokenType.REFRESH.name())) {
            throw new CustomException(Exceptions.NOT_REFRESH_TOKEN);
        }
        if (blackList.containsToken(refreshToken)) {
            throw new CustomException(Exceptions.BLACKLISTED_TOKEN);
        }
        Long roomId = Long.parseLong(claims.getSubject());
        return new TokenResponseDTO(createToken(roomId, TokenType.ACCESS));
    }

    /*로그아웃*/
    public void logout(String refreshToken) {
        Date refreshTokenExpiration = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(refreshToken).getPayload().getExpiration();

        blackList.putToken(refreshToken, refreshTokenExpiration.toString());
    }

    /*토큰 유효성 확인 및 유저 ID 추출*/
    public Long getRoomId(HttpServletRequest request) {
        try {
            String token = this.resolveToken(request);
            Claims claims = Jwts.parser().verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Date now = new Date();

            if (blackList.containsToken(token)) {
                throw new CustomException(Exceptions.BLACKLISTED_TOKEN);
            }
            if (claims.getExpiration().before(now)) {
                throw new CustomException(Exceptions.EXPIRED_TOKEN);
            }
            if (claims.get("type") == null) {
                throw new CustomException(Exceptions.INVALID_TOKEN_TYPE);
            }
            if (!claims.get("type").equals(TokenType.ACCESS.name())) {
                throw new CustomException(Exceptions.NOT_ACCESS_TOKEN);
            }
            return Long.parseLong(claims.getSubject());
        } catch (JwtException e) {
            throw new CustomException(Exceptions.INVALID_TOKEN);
        }
    }


    /*요청 헤더에서 토큰 추출*/
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        throw new CustomException(Exceptions.NO_TOKEN);
    }
}
