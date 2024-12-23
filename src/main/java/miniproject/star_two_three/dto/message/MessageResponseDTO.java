package miniproject.star_two_three.dto.message;

import lombok.Data;

@Data
public class MessageResponseDTO {
    private final Long id;
    private final String sender;
    private final String body;

    public MessageResponseDTO(Long id, String sender, String body) {
        this.id = id;
        this.sender = sender;
        this.body = body;
    }
}
