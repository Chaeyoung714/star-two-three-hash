package miniproject.star_two_three.dto.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoomResponseDTO {

    @JsonProperty("room_signature")
    private String roomSignature;

    @JsonProperty("access_token")
    private String accessToken;

    public RoomResponseDTO(String roomSignature, String accessToken) {
        this.roomSignature = roomSignature;
        this.accessToken = accessToken;
    }
}
