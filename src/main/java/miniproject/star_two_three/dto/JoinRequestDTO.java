package miniproject.star_two_three.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JoinRequestDTO {

    @JsonProperty("login_id")
    private String loginId;

    private String nickname;

    private String password;

    public JoinRequestDTO(String loginId, String nickname, String password) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
    }
}
