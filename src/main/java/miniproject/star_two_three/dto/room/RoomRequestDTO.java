package miniproject.star_two_three.dto.room;

import lombok.Data;

@Data
public class RoomRequestDTO {

    private String title;

    private String password;

    public RoomRequestDTO(String title, String password) {
        this.title = title;
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public String getPassword() {
        return password;
    }
}
