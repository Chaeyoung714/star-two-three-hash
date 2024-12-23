package miniproject.star_two_three.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequestDTO {
    @NotNull
    @JsonProperty("room_signature")
    private String roomSignature;

    @NotNull
    private String sender;

    @NotNull
    private String body;

    public MessageRequestDTO(String roomSignature, String sender, String body) {
        this.roomSignature = roomSignature;
        this.sender = sender;
        this.body = body;
    }
}
