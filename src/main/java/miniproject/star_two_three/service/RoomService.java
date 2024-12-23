package miniproject.star_two_three.service;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.domain.Room;
import miniproject.star_two_three.dto.jwt.TokenResponseDTO;
import miniproject.star_two_three.dto.room.LoginRequestDTO;
import miniproject.star_two_three.dto.room.RoomRequestDTO;
import miniproject.star_two_three.dto.room.RoomResponseDTO;
import miniproject.star_two_three.exception.CustomException;
import miniproject.star_two_three.exception.Exceptions;
import miniproject.star_two_three.security.jwt.JwtProvider;
import miniproject.star_two_three.security.jwt.TokenType;
import miniproject.star_two_three.repository.RoomRepository;
import miniproject.star_two_three.security.util.CookieParser;
import miniproject.star_two_three.security.util.HashEncoder;
import miniproject.star_two_three.security.util.HashDecoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final JwtProvider jwtProvider;

    public ResponseEntity<RoomResponseDTO> createRoom(RoomRequestDTO request) {
        //TODO : 값 검증
        String password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        Room room = new Room(request.getTitle(), password);
        roomRepository.save(room);

        String hashedRoomId = HashEncoder.encryptLongValue(room.getId());
        room.setSignature(hashedRoomId);

        String accessToken = jwtProvider.createToken(room.getId(), TokenType.ACCESS);
        String refreshToken = jwtProvider.createToken(room.getId(), TokenType.REFRESH);
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(new RoomResponseDTO(room.getSignature()));
    }

    public ResponseEntity<TokenResponseDTO> login(LoginRequestDTO request) {
        try {
            Long roomId = Long.valueOf(HashDecoder.decrypt(request.getRoomSignature()));
            Room room = findRoomByIdOrElseException(roomId);
            if (BCrypt.checkpw(request.getPassword(), room.getPassword())) {
                String accessToken = jwtProvider.createToken(room.getId(), TokenType.ACCESS);
                String refreshToken = jwtProvider.createToken(room.getId(), TokenType.REFRESH);
                ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .sameSite("Strict")
                        .build();
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                        .body(new TokenResponseDTO(accessToken));
            }
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        } catch (NoResultException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    public ResponseEntity<TokenResponseDTO> reissueToken(HttpServletRequest request) {
        try {
            String refreshToken = CookieParser.parseRefreshToken(request);
            TokenResponseDTO tokens = jwtProvider.refreshAccessToken(refreshToken);
            return ResponseEntity.ok()
                    .body(tokens);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
    }

    private Room findRoomByIdOrElseException(Long roomId) {
        Optional<Room> room = roomRepository.findByRoomId(roomId);
        if (room.isEmpty()) {
            throw new CustomException(Exceptions.ROOM_NOT_FOUND);
        }
        return room.get();
    }
}
