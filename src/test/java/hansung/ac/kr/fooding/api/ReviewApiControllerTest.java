package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.domain.Comment;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;


@SpringBootTest
@Transactional
class ReviewApiControllerTest {
    @Autowired ReviewRepository reviewRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void getAReview() throws Exception {
//        Review review = reviewRepository.findById(3L).orElse(null);
        Review result = reviewRepository.findReviewWithComments(3L).orElse(null);
        List<Comment> comments = result.getComments();
        for (Comment comment : comments) {
            System.out.println("comment = " + comment.getContent());
        }
    }


}