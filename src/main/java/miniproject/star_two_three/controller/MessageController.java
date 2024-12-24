package miniproject.star_two_three.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.dto.message.MessageResponseDTO;
import miniproject.star_two_three.dto.message.MessageRequestDTO;
import miniproject.star_two_three.dto.message.PagedMessagesResponseDTO;
import miniproject.star_two_three.security.jwt.JwtProvider;
import miniproject.star_two_three.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final JwtProvider jwtProvider;

    @GetMapping("/get/received/all")
    public ResponseEntity<PagedMessagesResponseDTO> getMessageList(
            HttpServletRequest request
            , @RequestParam(name = "page") int page
    ) {
        Long roomId = jwtProvider.getRoomId(request);
        return messageService.readPaginatedMessageList(roomId, page);
    }

    @GetMapping("/get/received/{messageId}")
    public ResponseEntity<MessageResponseDTO> getDetailMessage(
            HttpServletRequest request
            , @PathVariable(name = "messageId") Long messageId
    ) {
        Long roomId = jwtProvider.getRoomId(request);
        return messageService.readDetailMessage(roomId, messageId);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponseDTO> postMessage(
            @Valid @RequestBody MessageRequestDTO messageRequestDTO
            ) {
        return messageService.createMessage(messageRequestDTO);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<Void> deleteMessage(
            HttpServletRequest request
            , @PathVariable(name = "messageId") Long messageId
    ) {
        Long roomId = jwtProvider.getRoomId(request);
        return messageService.deleteMessage(roomId, messageId);
    }

}
