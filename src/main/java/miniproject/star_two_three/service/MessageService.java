package miniproject.star_two_three.service;

import jakarta.persistence.NoResultException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.domain.Message;
import miniproject.star_two_three.domain.Room;
import miniproject.star_two_three.dto.message.MessageResponseDTO;
import miniproject.star_two_three.dto.message.MessageRequestDTO;
import miniproject.star_two_three.repository.MessageRepository;
import miniproject.star_two_three.repository.RoomRepository;
import miniproject.star_two_three.util.HashDecoder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;

    public ResponseEntity<List<MessageResponseDTO>> readPaginatedMessageList(Long roomId) {
        //TODO : 페이지네이션
        List<Message> messages = messageRepository.findAllByRoomId(roomId);
        List<MessageResponseDTO> response = messages.stream()
                .map(m -> new MessageResponseDTO(m.getId(), m.getNickname(), m.getBody()))
                .toList();
        return ResponseEntity.ok()
                .body(response);
    }

    public ResponseEntity<MessageResponseDTO> readDetailMessage(Long roomId, Long messageId) {
        try {
            Message message = messageRepository.findById(messageId);
            return ResponseEntity.ok()
                    .body(new MessageResponseDTO(message.getId(), message.getNickname(), message.getBody()));
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); //TODO : 응답형식 통일하기
        }
    }

    public ResponseEntity<MessageResponseDTO> createMessage(MessageRequestDTO request) {
        try {
            Long roomId = Long.valueOf(HashDecoder.decrypt(request.getRoomSignature()));
            Room room = roomRepository.findByRoomId(roomId);
            Message message = new Message(request.getNickname(), request.getBody(), room);
            messageRepository.save(message);
            room.updateMessage(message);
            return ResponseEntity.ok()
                    .body(new MessageResponseDTO(message.getId(), message.getNickname(), message.getBody()));

        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    public ResponseEntity<String> deleteMessage(Long roomId, Long messageId) {
        try {
            Message message = messageRepository.findById(messageId);
            messageRepository.delete(message);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("successfully deleted");
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }
}
