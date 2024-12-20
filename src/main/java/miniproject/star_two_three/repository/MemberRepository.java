package miniproject.star_two_three.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import miniproject.star_two_three.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;


    public void save(Member member) {
        em.persist(member);
    }

    public Member findByLoginId(String loginId) {
        return em.createQuery("select m from Member m"
                                + " where m.loginId = :loginId",
                        Member.class)
                .setParameter("loginId", loginId)
                .getSingleResult();
    }
}
