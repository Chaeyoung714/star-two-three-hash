package miniproject.star_two_three.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String password;

    private String url;

    public LoginRequestDTO(String password, String url) {
        this.password = password;
        this.url = url;
    }
}
