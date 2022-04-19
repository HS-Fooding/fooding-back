package hansung.ac.kr.fooding.api;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hansung.ac.kr.fooding.domain.*;
import hansung.ac.kr.fooding.dto.review.ReviewDetailResDTO;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.CommentRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.ReviewRepository;
import hansung.ac.kr.fooding.service.CommentService;
import hansung.ac.kr.fooding.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
    ReviewRepository reviewRepository;
    @Autowired
    ReviewService reviewService;
    @Autowired
    CommentRepository commentRepository;

    // 리뷰 작성
    @Test
    public void postReview() throws Exception {
        Account account = accountRepository.findByIdentifier("testIdentifier");
        Restaurant restaurant = restaurantRepository.findByName("restName").orElse(null);

        // then
        assertThat(account.getIdentifier()).isEqualTo("testIdentifier");
        assertThat(restaurant.getName()).isEqualTo("restName");

        assertThat(restaurant.getReviews().get(0).getTitle()).isEqualTo("title1");
        assertThat(restaurant.getReviews().get(0).getContent()).isEqualTo("review1");
        assertThat(restaurant.getReviews().get(1).getTitle()).isEqualTo("title2");
        assertThat(restaurant.getReviews().get(1).getContent()).isEqualTo("review2");
        assertThat(restaurant.getReviews().size()).isEqualTo(2);

        assertThat(restaurant.getAdmin()).isEqualTo(account);
    }

    // 리뷰 목록 가져오기
    @Test
    public void getReviews() throws Exception {
        // given
        Account user1 = accountRepository.findByIdentifier("userID");
        Account admin = accountRepository.findByIdentifier("adminID");
        Restaurant restaurant = restaurantRepository.findByName("restName").orElse(null);

        // when
        List<Review> reviews = reviewService.getReviewsOnly(restaurant.getId());

        // then
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getAuthor()).isEqualTo(user1);
        assertThat(restaurant.getAdmin()).isEqualTo(admin);
    }


    // 단일 리뷰 가져오기 (댓글도 함께 가져옴)
    @Test
    public void getReview() throws Exception {
        // given
        Restaurant restaurant = restaurantRepository.findByName("restName").orElse(null);
        assertThat(restaurant.getAdmin().getIdentifier()).isEqualTo("adminID");
        assertThat(restaurant.getReviews().size()).isEqualTo(2);

        Review review = restaurant.getReviews().get(0); // 첫 번째 리뷰 가져오기
        assertThat(review.getTitle()).isEqualTo("title1");
        assertThat(review.getContent()).isEqualTo("review1");
        assertThat(review.getComments().size()).isEqualTo(2);

        //then
        ReviewDetailResDTO result = reviewService.findReviewWithComments(review.getId());
        assertThat(result.getComments().size()).isEqualTo(2);
        assertThat(result.getComments().get(0).getContent()).isEqualTo("comment1");
    }

    // 리뷰 지우기 (댓글도 함께)
    @Test
    public void deleteReview() throws Exception {
        // given
        Restaurant restaurant = restaurantRepository.findByName("restName").orElse(null);
        Review review = restaurant.getReviews().get(0);

        assertThat(restaurant.getReviews().size()).isEqualTo(2);
        assertThat(review.getComments().get(0).getContent()).isEqualTo("comment1");

        Comment comment = review.getComments().get(0);

        // when
        reviewService.deleteReview(review.getId());

        em.flush();
        em.clear();

        // then
        Restaurant _restaurant = restaurantRepository.findByName("restName").orElse(null);
        Review _review = _restaurant.getReviews().get(0);

        assertThat(_restaurant.getReviews().size()).isEqualTo(1);
        assertThat(_review.getContent()).isEqualTo("review2");

        Comment _comment = commentRepository.findById(comment.getId()).orElse(null);
        assertThat(_comment).isNull();
    }

    // querydsl 동작 확인
    /*@Test
    public void querydslTest() throws Exception {
        JPAQueryFactory query = new JPAQueryFactory(em);
        List<Member> result = query
                .selectFrom(QMember.member)
                .fetch();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("adminName");
    }*/
}