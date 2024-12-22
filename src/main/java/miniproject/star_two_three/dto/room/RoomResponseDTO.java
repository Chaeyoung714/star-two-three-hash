package miniproject.star_two_three.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoomResponseDTO {

    @JsonProperty("room_signature")
    private String roomSignature;

    public RoomResponseDTO(String roomSignature) {
        this.roomSignature = roomSignature;
    }
}
