package miniproject.star_two_three.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class PagedMessagesResponseDTO {
    private final int page;

    private final int size;

    @JsonProperty("total_pages")
    private final int totalPages;

    @JsonProperty("total_messages")
    private final long totalMessages;

    private final List<MessageResponseDTO> messages;

    public PagedMessagesResponseDTO(int page, int size, int totalPages, long totalMessages,
                                    List<MessageResponseDTO> messages) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalMessages = totalMessages;
        this.messages = messages;
    }
}
