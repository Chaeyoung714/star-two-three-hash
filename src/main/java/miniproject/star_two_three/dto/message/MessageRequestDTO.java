package miniproject.star_two_three.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRequestDTO {

    @JsonProperty("room_signature")
    private String roomSignature;

    private String nickname;

    private String body;

    public MessageRequestDTO(String roomSignature, String nickname, String body) {
        this.roomSignature = roomSignature;
        this.nickname = nickname;
        this.body = body;
    }
}
