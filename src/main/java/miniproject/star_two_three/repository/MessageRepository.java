package miniproject.star_two_three.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import miniproject.star_two_three.domain.Message;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Message message) {
        em.persist(message);
    }

    public Optional<Message> findById(Long messageId) {
        try {
            return Optional.ofNullable(em.createQuery("select m from Message m"
                                    + " where m.id = :messageId",
                            Message.class)
                    .setParameter("messageId", messageId)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Message> findAllByRoomId(Long roomId) {
        return em.createQuery("select m from Message m"
                                + " where m.room.id = :roomId",
                        Message.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }

    public void delete(Message message) {
        em.remove(message);
    }
}
