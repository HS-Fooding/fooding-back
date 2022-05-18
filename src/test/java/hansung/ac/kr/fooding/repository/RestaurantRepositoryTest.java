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

    @Test
    public void find(){
        //given
        PersistenceUtil persistenceUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
        Restaurant restaurant1 = restaurantRepository.findTest1(17L);
        Restaurant restaurant2 = restaurantRepository.findTest2(17L);
        Restaurant restaurant3 = restaurantRepository.findById(17L).get();
        Menu menu = menuRepository.findById(restaurant1.getMenus().get(0).getId()).get();
        Image fromRestwithoutEntityGraph = restaurant1.getMenus().get(0).getImage();
        Image fromRestwithEntityGraph = restaurant2.getMenus().get(0).getImage();
        Image fromMenu = menu.getImage();

        boolean result1 = persistenceUtil.isLoaded(fromRestwithoutEntityGraph);
        boolean result2 = persistenceUtil.isLoaded(fromRestwithEntityGraph);
        boolean result3 = persistenceUtil.isLoaded(fromMenu);

        //then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }
}