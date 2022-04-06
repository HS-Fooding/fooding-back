package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.domain.*;
import hansung.ac.kr.fooding.dto.ReviewPostDTO;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class ReviewApiControllerTest {
    @Autowired RestaurantRepository restaurantRepository;
    @Autowired AccountRepository accountRepository;
    @Autowired ReviewService reviewService;

    @Test
    public void postReview() throws Exception {
        Account account = accountRepository.findByIdentifier("testIdentifier");
        Restaurant restaurant = restaurantRepository.findByName("restName");

        // 리뷰 작성
        ReviewPostDTO reviewPostDTO1 = new ReviewPostDTO("title1", "content1", 1.5f);
        ReviewPostDTO reviewPostDTO2 = new ReviewPostDTO("title2", "content2", 2.5f);

        reviewService.postReview(account, reviewPostDTO1, null, restaurant.getId());
        reviewService.postReview(account, reviewPostDTO2, null, restaurant.getId());


        // 검증
        assertThat(account.getIdentifier()).isEqualTo("testIdentifier");
        assertThat(restaurant.getName()).isEqualTo("restName");

        assertThat(restaurant.getReviews().get(0).getTitle()).isEqualTo("title1");
        assertThat(restaurant.getReviews().get(0).getContent()).isEqualTo("content1");
        assertThat(restaurant.getReviews().get(1).getTitle()).isEqualTo("title2");
        assertThat(restaurant.getReviews().get(1).getContent()).isEqualTo("content2");
        assertThat(restaurant.getReviews().size()).isEqualTo(2);
    }
}