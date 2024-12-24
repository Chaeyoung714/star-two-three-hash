package miniproject.star_two_three.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomTitleRequestDTO {

    @NotNull
    @JsonProperty("room_signature")
    private String roomSignature;

    public RoomTitleRequestDTO(String roomSignature) {
        this.roomSignature = roomSignature;
    }
}
