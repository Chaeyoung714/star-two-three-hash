package miniproject.star_two_three.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotNull
    private String password;

    @JsonProperty("room_signature")
    private String roomSignature;

    public LoginRequestDTO(String password, String roomSignature) {
        this.password = password;
        this.roomSignature = roomSignature;
    }
}
