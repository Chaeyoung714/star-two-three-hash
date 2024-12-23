package miniproject.star_two_three.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @NotNull @NotEmpty(message = "Sender nickname must not be empty.")
    @Size(min = 1, max = 10, message = "Sender nickname length must be 1 to 10.")
    private String sender;

    @NotNull @NotEmpty(message = "Message text must not be empty.")
    @Size(min = 1, max = 500, message = "Message text length must be 1 to 500.")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public Message(Room room, String body, String sender) {
        this.room = room;
        this.body = body;
        this.sender = sender;
    }
}
