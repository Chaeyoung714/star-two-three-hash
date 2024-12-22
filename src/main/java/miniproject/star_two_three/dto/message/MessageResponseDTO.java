package miniproject.star_two_three.dto.message;

import lombok.Data;

@Data
public class MessageResponseDTO {
    private final Long id;
    private final String nickname;
    private final String body;

    public MessageResponseDTO(Long id, String nickname, String body) {
        this.id = id;
        this.nickname = nickname;
        this.body = body;
    }
}
