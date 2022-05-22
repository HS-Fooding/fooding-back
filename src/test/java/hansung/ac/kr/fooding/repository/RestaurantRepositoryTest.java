package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Menu;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.image.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUtil;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestaurantRepositoryTest {
    @Autowired RestaurantRepository restaurantRepository;
    @Autowired MenuRepository menuRepository;
    @PersistenceContext
    EntityManager em;

}