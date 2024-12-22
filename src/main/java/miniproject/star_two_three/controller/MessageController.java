package miniproject.star_two_three.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.dto.message.MessageResponseDTO;
import miniproject.star_two_three.dto.message.MessageRequestDTO;
import miniproject.star_two_three.exception.CustomException;
import miniproject.star_two_three.jwt.JwtProvider;
import miniproject.star_two_three.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final JwtProvider jwtProvider;

    @GetMapping("/get/received")
    public ResponseEntity<List<MessageResponseDTO>> getMessageList(
            HttpServletRequest request
    ) {
        try {
            Long roomId = jwtProvider.getRoomId(request);
            return messageService.readMessageList(roomId);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(null); //TODO : 메세지 함께 응답
        }
    }

    @GetMapping("/get/received/{messageId}")
    public ResponseEntity<MessageResponseDTO> getDetailMessage(
            HttpServletRequest request
            , @PathVariable(name = "messageId") Long messageId
    ) {
        try {
            Long roomId = jwtProvider.getRoomId(request);
            return messageService.readDetailMessage(roomId, messageId);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(null); //TODO : 메세지 함께 응답
        }
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponseDTO> postMessage(
            @RequestBody MessageRequestDTO messageRequestDTO
            ) {
        return messageService.createMessage(messageRequestDTO);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<String> deleteMessage(
            HttpServletRequest request
            , @PathVariable(name = "messageId") Long messageId
    ) {
        try {
            Long roomId = jwtProvider.getRoomId(request);
            return messageService.deleteMessage(roomId, messageId);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(null); //TODO : 메세지 함께 응답
        }
    }

}
