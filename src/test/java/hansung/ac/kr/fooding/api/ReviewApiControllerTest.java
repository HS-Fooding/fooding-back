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


    @Test
    public void getReview() throws Exception {
        // given
        Account account = accountRepository.findByIdentifier("testIdentifier");
        Restaurant restaurant = restaurantRepository.findByName("restName");
        Review review = restaurant.getReviews().get(0);

        // when
        CommentPostDTO dto1 = new CommentPostDTO(null, "comment1");
        CommentPostDTO dto2 = new CommentPostDTO(null, "comment2");
        CommentPostDTO dto3 = new CommentPostDTO(null, "comment3");
        CommentPostDTO dto4 = new CommentPostDTO(null, "comment4");

        commentService.postComment(review.getId(), account, dto1);
        commentService.postComment(review.getId(), account, dto2);
        commentService.postComment(review.getId(), account, dto3);
        commentService.postComment(review.getId(), account, dto4);

        em.flush();
        em.clear();

        //then
        ReviewDetailResDTO result = reviewService.findReviewWithComments(review.getId());

        assertThat(result.getComments().size()).isEqualTo(4);
        assertThat(result.getTitle()).isEqualTo("title1");
    }
}