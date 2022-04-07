package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.domain.*;
import hansung.ac.kr.fooding.dto.CommentPostDTO;
import hansung.ac.kr.fooding.dto.ReviewDetailResDTO;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.service.CommentService;
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
    @Autowired EntityManager em;
    @Autowired RestaurantRepository restaurantRepository;
    @Autowired AccountRepository accountRepository;
    @Autowired ReviewService reviewService;
    @Autowired CommentService commentService;

    // 리뷰 작성
    @Test
    public void postReview() throws Exception {
        Account account = accountRepository.findByIdentifier("testIdentifier");
        Restaurant restaurant = restaurantRepository.findByName("restName");

        // then
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

        // when
        List<Review> reviews = reviewService.getReviewsOnly(restaurant.getId());

        // then
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(restaurant.getAdmin()).isEqualTo(account);
        assertThat(reviews.get(0).getAuthor()).isEqualTo(account);
    }


    // 단일 리뷰 가져오기 (댓글도 함께 가져옴)
    @Test
    public void getReview() throws Exception {
        // given
        Restaurant restaurant = restaurantRepository.findByName("restName");
        assertThat(restaurant.getAdmin().getIdentifier()).isEqualTo("adminID");
        assertThat(restaurant.getReviews().size()).isEqualTo(2);

        Review review = restaurant.getReviews().get(0); // 첫 번째 리뷰 가져오기
        assertThat(review.getTitle()).isEqualTo("title1");
        assertThat(review.getContent()).isEqualTo("content1");
        assertThat(review.getComments().size()).isEqualTo(2);

        //then
        ReviewDetailResDTO result = reviewService.findReviewWithComments(review.getId());
        assertThat(result.getComments().size()).isEqualTo(2);
        assertThat(result.getComments().get(0).getContent()).isEqualTo("comment1");
    }
}