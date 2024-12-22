package miniproject.star_two_three.dto.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenResponseDTO {

    @JsonProperty("access_token")
    private String accessToken;

    public TokenResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
