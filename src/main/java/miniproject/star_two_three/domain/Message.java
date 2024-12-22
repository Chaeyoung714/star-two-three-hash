package miniproject.star_two_three.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private String nickname;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id") //FK이자 연관관계의 주인필드
    private Room room;

    public Message(String nickname, String body, Room room) {
        this.nickname = nickname;
        this.body = body;
        this.room = room;
    }
}
