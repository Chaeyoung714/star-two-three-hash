package miniproject.star_two_three.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoomTitleRequestDTO {

    @JsonProperty("room_signature")
    private String roomSignature;

    public RoomTitleRequestDTO(String roomSignature) {
        this.roomSignature = roomSignature;
    }
}
