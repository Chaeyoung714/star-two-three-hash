package miniproject.star_two_three.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String loginId;
    private String password;

    public LoginRequestDTO(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
