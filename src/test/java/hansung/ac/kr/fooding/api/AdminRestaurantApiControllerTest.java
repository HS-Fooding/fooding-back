package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.repository.MemberRepository;
import hansung.ac.kr.fooding.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
class AdminRestaurantApiControllerTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    EntityManager em;


    @Test
    public void test1() {
        String jpql = "select f from Member m join fetch m.favor f";
        List<Favor> resultList = em.createQuery(jpql, Favor.class)
                .getResultList();
        for (Object o : resultList) {
            System.out.println("o = " + o);
        }
    }

}