package miniproject.star_two_three.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;

@Data
public class MessageResponseDTO {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Long id;
    private final String sender;
    @JsonProperty("created_at")
    private final String createdAt;
    private final String body;

    public MessageResponseDTO(Long id, String sender, LocalDateTime createdAt, String body) {
        this.id = id;
        this.sender = sender;
        this.createdAt = createdAt.format(formatter);
        this.body = body;
    }
}
