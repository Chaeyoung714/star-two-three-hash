package miniproject.star_two_three.service;

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
    private final HashDecoder hashDecoder;
    private final HashEncoder hashEncoder;


    public ResponseEntity<RoomResponseDTO> createRoom(RoomRequestDTO request) {
        validatePassword(request.getPassword());
        String password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        Room room = new Room(request.getTitle(), password);
        roomRepository.save(room);

        String hashedRoomId = hashEncoder.encryptLongValue(room.getId());
        room.setSignature(hashedRoomId);

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
                .body(new RoomResponseDTO(room.getSignature(), accessToken));
    }

    private void validatePassword(String password) {
        if (password == null || !password.matches("\\d{4}")) {
            throw new CustomException(Exceptions.INVALID_PASSWORD);
        }
    }

    public ResponseEntity<TokenResponseDTO> login(LoginRequestDTO request) {
        Long roomId = Long.valueOf(hashDecoder.decrypt(request.getRoomSignature()));
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
        throw new CustomException(Exceptions.WRONG_PASSWORD);
    }

    public ResponseEntity<TokenResponseDTO> reissueToken(HttpServletRequest request) {
        String refreshToken = CookieParser.parseRefreshToken(request);
        TokenResponseDTO tokens = jwtProvider.refreshAccessToken(refreshToken);
        return ResponseEntity.ok()
                .body(tokens);
    }

    private Room findRoomByIdOrElseException(Long roomId) {
        Optional<Room> room = roomRepository.findByRoomId(roomId);
        if (room.isEmpty()) {
            throw new CustomException(Exceptions.ROOM_NOT_FOUND);
        }
        return room.get();
    }
}
