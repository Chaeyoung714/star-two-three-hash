package miniproject.star_two_three.dto.room;

import lombok.Data;

@Data
public class RoomTitleResponseDTO {
    private String title;

    public RoomTitleResponseDTO(String title) {
        this.title = title;
    }
}
