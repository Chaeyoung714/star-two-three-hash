package miniproject.star_two_three.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import miniproject.star_two_three.domain.Room;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Room room) {
        em.persist(room);
    }

    public Room findByRoomId(Long roomId) {
        return em.createQuery("select r from Room r"
                                + " where r.id = :roomId",
                        Room.class)
                .setParameter("roomId", roomId)
                .getSingleResult();
    }
}
