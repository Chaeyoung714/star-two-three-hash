package miniproject.star_two_three.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @JsonProperty("login_id")
    private String loginId;

    private String password;

    public LoginRequestDTO(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
