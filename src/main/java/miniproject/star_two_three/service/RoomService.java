package miniproject.star_two_three.service;

import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.domain.Room;
import miniproject.star_two_three.dto.room.RoomRequestDTO;
import miniproject.star_two_three.dto.room.RoomResponseDTO;
import miniproject.star_two_three.jwt.JwtProvider;
import miniproject.star_two_three.jwt.TokenType;
import miniproject.star_two_three.repository.RoomRepository;
import miniproject.star_two_three.util.HashConverter;
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

    public ResponseEntity<RoomResponseDTO> createRoom(RoomRequestDTO requestDTO) {
        String password = HashConverter.convertStringValue(requestDTO.getPassword());
        Room room = new Room(requestDTO.getTitle(), password);
        roomRepository.save(room);

        String hashedRoomId = HashConverter.convertLongValue(room.getId());
        room.setUrl(hashedRoomId);

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
                .body(new RoomResponseDTO(room.getUrl()));
    }
}
