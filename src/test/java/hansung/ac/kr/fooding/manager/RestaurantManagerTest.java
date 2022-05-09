package hansung.ac.kr.fooding.manager;

import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.exception.C_IllegalStateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.security.test.context.support.WithMockUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@WithMockUser(username = "adminID1")
class RestaurantManagerTest {
    @Autowired RestaurantManager restaurantManager;

    @Test
    void getRestaurant() throws C_IllegalStateException {
        //when
        Restaurant restaurant = restaurantManager.getRestaurant(1L);

        //then
        assertThat(restaurant.getId()).isEqualTo(1L);
        assertThrows(C_IllegalStateException.class, () -> {
           restaurantManager.getRestaurant(9999L);
        });
    }

    @Test
    void getRestaurantList() {
        Slice<Object> slice = restaurantManager.getRestaurantList("true", null);
        System.out.println(slice);
    }

    @Test
    void searchByKeyword() {
    }

    @Test
    void getRestaurantByCoord() {
    }

    @Test
    void saveRestaurant() {
    }

    @Test
    void testSaveRestaurant() {
    }

    @Test
    void addImagesToRestaurant() {
    }

    @Test
    void addImageToRestaurant() {
    }

    @Test
    void getRestInfo() {
    }

    @Test
    void getRestaurantStructure() {
    }
}