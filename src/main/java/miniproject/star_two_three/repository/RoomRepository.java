package miniproject.star_two_three.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import miniproject.star_two_three.domain.Room;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Room room) {
        em.persist(room);
    }

    public Optional<Room> findByRoomId(Long roomId) {
        try {
            return Optional.ofNullable(em.createQuery("select r from Room r"
                                    + " where r.id = :roomId",
                            Room.class)
                    .setParameter("roomId", roomId)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
