package miniproject.star_two_three.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String title;

    private String password;

    private String url;

    @OneToMany(mappedBy = "room")
    private List<Message> messages;

    public Room(String title, String password) {
        this.title = title;
        this.password = password;
        this.messages = new ArrayList<>();
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
