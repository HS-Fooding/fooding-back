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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class ReviewApiControllerTest {
    @Autowired
    EntityManager em;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ReviewService reviewService;

    // 리뷰 작성
    @Test
    public void postReview() throws Exception {
        // given
        Account account = accountRepository.findByIdentifier("testIdentifier");
        Restaurant restaurant = restaurantRepository.findByName("restName");

        // when
        ReviewPostDTO reviewPostDTO1 = new ReviewPostDTO("title1", "content1", 1.5f);
        ReviewPostDTO reviewPostDTO2 = new ReviewPostDTO("title2", "content2", 2.5f);

        reviewService.postReview(account, reviewPostDTO1, null, restaurant.getId());
        reviewService.postReview(account, reviewPostDTO2, null, restaurant.getId());

        // when
        assertThat(account.getIdentifier()).isEqualTo("testIdentifier");
        assertThat(restaurant.getName()).isEqualTo("restName");

        assertThat(restaurant.getReviews().get(0).getTitle()).isEqualTo("title1");
        assertThat(restaurant.getReviews().get(0).getContent()).isEqualTo("content1");
        assertThat(restaurant.getReviews().get(1).getTitle()).isEqualTo("title2");
        assertThat(restaurant.getReviews().get(1).getContent()).isEqualTo("content2");
        assertThat(restaurant.getReviews().size()).isEqualTo(2);

        assertThat(restaurant.getAdmin()).isEqualTo(account);
    }

    // 리뷰 목록 가져오기
    @Test
    public void getReviews() throws Exception {
        // given
        Account account = accountRepository.findByIdentifier("testIdentifier");
        Restaurant restaurant = restaurantRepository.findByName("restName");

        ReviewPostDTO reviewPostDTO1 = new ReviewPostDTO("title1", "content1", 1.5f);
        ReviewPostDTO reviewPostDTO2 = new ReviewPostDTO("title2", "content2", 2.5f);

        reviewService.postReview(account, reviewPostDTO1, null, restaurant.getId());
        reviewService.postReview(account, reviewPostDTO2, null, restaurant.getId());

        // when
        List<Review> reviews = reviewService.getReviewsOnly(restaurant.getId());

        // then
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(restaurant.getAdmin()).isEqualTo(account);
        assertThat(reviews.get(0).getAuthor()).isEqualTo(account);
    }
}