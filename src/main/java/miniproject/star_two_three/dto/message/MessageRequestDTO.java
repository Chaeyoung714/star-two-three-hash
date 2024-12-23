package miniproject.star_two_three.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRequestDTO {

    @JsonProperty("room_signature")
    private String roomSignature;

    private String sender;

    private String body;

    public MessageRequestDTO(String roomSignature, String sender, String body) {
        this.roomSignature = roomSignature;
        this.sender = sender;
        this.body = body;
    }
}
