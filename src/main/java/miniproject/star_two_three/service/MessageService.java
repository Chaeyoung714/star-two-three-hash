package miniproject.star_two_three.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import miniproject.star_two_three.domain.Message;
import miniproject.star_two_three.domain.Room;
import miniproject.star_two_three.dto.message.MessageResponseDTO;
import miniproject.star_two_three.dto.message.MessageRequestDTO;
import miniproject.star_two_three.dto.message.PagedMessagesResponseDTO;
import miniproject.star_two_three.exception.CustomException;
import miniproject.star_two_three.exception.Exceptions;
import miniproject.star_two_three.repository.MessageRepository;
import miniproject.star_two_three.repository.RoomRepository;
import miniproject.star_two_three.security.util.HashDecoder;
import org.springframework.data.domain.Page;
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

    private static final int pageSize = 10;

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final HashDecoder hashDecoder;

    public ResponseEntity<PagedMessagesResponseDTO> readPaginatedMessageList(Long roomId, int pagePointer) {
        Pageable pageable = PageRequest.of(pagePointer, pageSize);
        Page<Message> pagedMessages = messageRepository.findPageByRoomId(roomId, pageable);
        List<MessageResponseDTO> response = pagedMessages.getContent().stream()
                .map(m -> new MessageResponseDTO(m.getId(), m.getSender(), m.getBody()))
                .toList();
        return ResponseEntity.ok()
                .body(new PagedMessagesResponseDTO(pagedMessages.getNumber()
                        , pagedMessages.getSize()
                        , pagedMessages.getTotalPages()
                        , pagedMessages.getTotalElements()
                        , response));
    }

    public ResponseEntity<MessageResponseDTO> readDetailMessage(Long roomId, Long messageId) {
        Message message = findMessageByIdOrElseException(messageId, roomId);
        return ResponseEntity.ok()
                .body(new MessageResponseDTO(message.getId(), message.getSender(), message.getBody()));
    }

    public ResponseEntity<MessageResponseDTO> createMessage(MessageRequestDTO request) {
        Long roomId = Long.valueOf(hashDecoder.decrypt(request.getRoomSignature()));
        Room room = findRoomByIdOrElseException(roomId);
        Message message = new Message(room, request.getBody(), request.getSender());
        messageRepository.save(message);
        room.updateMessage(message);
        return ResponseEntity.ok()
                .body(new MessageResponseDTO(message.getId(), message.getSender(), message.getBody()));
    }

    public ResponseEntity<Void> deleteMessage(Long roomId, Long messageId) {
        Message message = findMessageByIdOrElseException(messageId, roomId);
        messageRepository.delete(message);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private Message findMessageByIdOrElseException(Long messageId, Long roomId) {
        Optional<Message> message = messageRepository.findByIdOrEmpty(messageId);
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
