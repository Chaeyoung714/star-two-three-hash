package miniproject.star_two_three.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponseDTO {

    private String message;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    public LoginResponseDTO(ResponseStatus responseStatus, String accessToken, String refreshToken) {
        this.message = responseStatus.getMessage();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
