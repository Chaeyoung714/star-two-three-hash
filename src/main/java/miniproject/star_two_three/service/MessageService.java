package miniproject.star_two_three.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.domain.Message;
import miniproject.star_two_three.domain.Room;
import miniproject.star_two_three.dto.message.MessageResponseDTO;
import miniproject.star_two_three.dto.message.MessageRequestDTO;
import miniproject.star_two_three.exception.CustomException;
import miniproject.star_two_three.exception.Exceptions;
import miniproject.star_two_three.repository.MessageRepository;
import miniproject.star_two_three.repository.RoomRepository;
import miniproject.star_two_three.security.util.HashDecoder;
import miniproject.star_two_three.security.util.HashEncoder;
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
    private final HashDecoder hashDecoder;

    public ResponseEntity<List<MessageResponseDTO>> readPaginatedMessageList(Long roomId) {
        //TODO : 페이지네이션
        List<Message> messages = messageRepository.findAllByRoomId(roomId);
        List<MessageResponseDTO> response = messages.stream()
                .map(m -> new MessageResponseDTO(m.getId(), m.getSender(), m.getBody()))
                .toList();
        return ResponseEntity.ok()
                .body(response);
    }

    public ResponseEntity<MessageResponseDTO> readDetailMessage(Long roomId, Long messageId) {
        try {
            Message message = findMessageByIdOrElseException(messageId, roomId);
            return ResponseEntity.ok()
                    .body(new MessageResponseDTO(message.getId(), message.getSender(), message.getBody()));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); //TODO : 응답형식 통일하기
        }
    }

    public ResponseEntity<MessageResponseDTO> createMessage(MessageRequestDTO request) {
        try {
            Long roomId = Long.valueOf(hashDecoder.decrypt(request.getRoomSignature()));
            Room room = findRoomByIdOrElseException(roomId);
            Message message = new Message(room, request.getBody(), request.getSender());
            messageRepository.save(message);
            room.updateMessage(message);
            return ResponseEntity.ok()
                    .body(new MessageResponseDTO(message.getId(), message.getSender(), message.getBody()));

        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    public ResponseEntity<String> deleteMessage(Long roomId, Long messageId) {
        try {
            Message message = findMessageByIdOrElseException(messageId, roomId);
            messageRepository.delete(message);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("successfully deleted");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    private Message findMessageByIdOrElseException(Long messageId, Long roomId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isEmpty()) {
            throw new CustomException(Exceptions.MESSAGE_NOT_FOUND);
        }
        if (!message.get().getRoom().equals(findRoomByIdOrElseException(roomId))) {
            throw new CustomException(Exceptions.NO_READ_AUTHORITY);
        }
        return message.get();
    }

    private Room findRoomByIdOrElseException(Long roomId) {
        Optional<Room> room = roomRepository.findByRoomId(roomId);
        if (room.isEmpty()) {
            throw new CustomException(Exceptions.ROOM_NOT_FOUND);
        }
        return room.get();
    }
}
