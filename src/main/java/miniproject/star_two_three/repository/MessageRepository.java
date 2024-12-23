package miniproject.star_two_three.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import miniproject.star_two_three.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Message message) {
        em.persist(message);
    }

    public Optional<Message> findByIdOrEmpty(Long messageId) {
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

    public Page<Message> findPageByRoomId(Long roomId, Pageable pageable) {
        List<Message> messages = em.createQuery("select m from Message m"
                                + " where m.room.id = :roomId",
                        Message.class)
                .setParameter("roomId", roomId)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        Long totalElements = em.createQuery("select count(m) from Message m"
                                + " where m.room.id = :roomId",
                        Long.class)
                .setParameter("roomId", roomId)
                .getSingleResult();

        return new PageImpl<>(messages, pageable, totalElements);
    }

    public void delete(Message message) {
        em.remove(message);
    }
}
