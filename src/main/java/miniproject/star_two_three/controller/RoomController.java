package miniproject.star_two_three.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.dto.jwt.TokenResponseDTO;
import miniproject.star_two_three.dto.room.LoginRequestDTO;
import miniproject.star_two_three.dto.room.RoomRequestDTO;
import miniproject.star_two_three.dto.room.RoomResponseDTO;
import miniproject.star_two_three.dto.room.RoomTitleRequestDTO;
import miniproject.star_two_three.dto.room.RoomTitleResponseDTO;
import miniproject.star_two_three.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<RoomResponseDTO> postRoom(
            @Valid @RequestBody RoomRequestDTO requestDTO
    ) {
        return roomService.createRoom(requestDTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDTO> loginRoom(
           @Valid @RequestBody LoginRequestDTO requestDTO
            ) {
        return roomService.login(requestDTO);
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> logoutRoom(
            HttpServletRequest request
    ) {
        return roomService.logout(request);
    }

    @PostMapping("/token/reissue")
    public ResponseEntity<TokenResponseDTO> reissueToken(
            HttpServletRequest request
    ) {
        return roomService.reissueToken(request);
    }

    @GetMapping("/get/title")
    public ResponseEntity<RoomTitleResponseDTO> getRoomTitle(
            @Valid @RequestBody RoomTitleRequestDTO request
    ) {
        return roomService.getRoomTitle(request.getRoomSignature());
    }
}
