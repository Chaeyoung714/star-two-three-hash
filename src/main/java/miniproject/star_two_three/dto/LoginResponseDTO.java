package miniproject.star_two_three.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String message;
    private String accessToken;
    private String refreshToken;

    public LoginResponseDTO(ResponseStatus responseStatus, String accessToken, String refreshToken) {
        this.message = responseStatus.getMessage();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
