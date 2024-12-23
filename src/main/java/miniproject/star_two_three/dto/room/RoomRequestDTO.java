package miniproject.star_two_three.dto.room;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomRequestDTO {
    @NotNull
    private String title;

    @NotNull
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
