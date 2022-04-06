package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Comment;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.dto.CommentPostDTO;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.CommentRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.ReviewRepository;
import hansung.ac.kr.fooding.service.CommentService;
import hansung.ac.kr.fooding.service.ReviewService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class CommentApiControllerTest {
    @Autowired RestaurantRepository restaurantRepository;
    @Autowired AccountRepository accountRepository;
    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;
    @Autowired ReviewRepository reviewRepository;


    // 댓글 달기
    @Test
    public void postComment() throws Exception {
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

        // then
        List<Comment> all = commentRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(4);
    }
}
