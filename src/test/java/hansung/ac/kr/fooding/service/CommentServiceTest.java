package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Comment;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.dto.comment.CommentPostDTO;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {
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
    CommentService commentService;

    @Test
    public void commentUpdate() throws Exception {
        // given
        Restaurant restName = restaurantRepository.findByName("restName").orElse(null);
        List<Review> reviews = restName.getReviews();
        Review review = reviews.get(0);

        assertThat(review.getComments().size()).isEqualTo(2);
        assertThat(review.getComments().get(0).getContent()).isEqualTo("comment1");

        Comment comment = review.getComments().get(0);

        // when
        CommentPostDTO dto = new CommentPostDTO(null, "updated!");
        commentService.updateComment(review.getId(), comment.getId(), dto);

        em.flush();
        em.clear();

        // then
        Restaurant _restName = restaurantRepository.findByName("restName").orElse(null);
        List<Review> _reviews = _restName.getReviews();
        Review _review = _reviews.get(0);

        assertThat(_review.getComments().size()).isEqualTo(2);
        assertThat(_review.getComments().get(0).getContent()).isEqualTo("updated!");
    }

    @Test
    public void deleteComment() throws Exception {
        // given
        Restaurant restName = restaurantRepository.findByName("restName").orElse(null);
        List<Review> reviews = restName.getReviews();
        Review review = reviews.get(0);

        assertThat(review.getComments().size()).isEqualTo(2);
        assertThat(review.getComments().get(0).getContent()).isEqualTo("comment1");

        Comment comment = review.getComments().get(0);

        // when
        commentService.deleteComment(review.getId(), comment.getId());

        // then
        assertThat(review.getComments().size()).isEqualTo(1);
    }
}