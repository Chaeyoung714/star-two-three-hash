package miniproject.star_two_three.dto.room;

import lombok.Data;

@Data
public class RoomResponseDTO {
    private String url;

    public RoomResponseDTO(String url) {
        this.url = url;
    }
}
