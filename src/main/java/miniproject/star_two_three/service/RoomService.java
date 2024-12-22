package miniproject.star_two_three.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.domain.Room;
import miniproject.star_two_three.dto.room.LoginRequestDTO;
import miniproject.star_two_three.dto.room.LoginResponseDTO;
import miniproject.star_two_three.dto.room.ResponseStatus;
import miniproject.star_two_three.dto.room.RoomRequestDTO;
import miniproject.star_two_three.dto.room.RoomResponseDTO;
import miniproject.star_two_three.jwt.JwtProvider;
import miniproject.star_two_three.jwt.TokenType;
import miniproject.star_two_three.repository.RoomRepository;
import miniproject.star_two_three.util.HashEncoder;
import miniproject.star_two_three.util.HashDecoder;
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

    public ResponseEntity<RoomResponseDTO> createRoom(RoomRequestDTO request) {
        //TODO : 값 검증
        String password = request.getPassword(); //TODO : encrypt
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

    public LoginResponseDTO login(LoginRequestDTO request) {
        try {
            Long roomId = Long.valueOf(HashDecoder.decrypt(request.getRoomSignature()));
            Room room = roomRepository.findByRoomId(roomId);
            if (room.getSignature().equals(request.getRoomSignature())) {
                return new LoginResponseDTO(
                        ResponseStatus.SUCCESS,
                        jwtProvider.createToken(room.getId(), TokenType.ACCESS),
                        jwtProvider.createToken(room.getId(), TokenType.REFRESH)
                );
            }
            throw new IllegalArgumentException();
        } catch (NoResultException | IllegalArgumentException e) { //비번 틀렸을 때
            return new LoginResponseDTO(
                    ResponseStatus.FAIL,
                    "",
                    "");
        }
    }

//    public ResponseDTO<TokenDTO> tokenReissue(String refreshToken) {
//        TokenDTO tokens = jwtProvider.refreshAccessToken(refreshToken);
//        return new ResponseDTO<>(tokens, Responses.OK);
//    }
}
