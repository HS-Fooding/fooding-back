package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.dto.comment.CommentResDTO;
import hansung.ac.kr.fooding.dto.review.ReviewDetailResDTO;
import hansung.ac.kr.fooding.dto.review.ReviewSimpleResDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceTest {
    @Autowired
    ReviewService reviewService;

    @Test
    public void getReviewsWithSlice() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0, 3);

        // when
        Slice<ReviewSimpleResDTO> result = reviewService.getReviews(1L, pageRequest);

        // then
        assertThat(result.getContent().size()).isEqualTo(2);
    }

    @Test
    public void findReviewWithComments() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0, 3);

        ReviewDetailResDTO dto = reviewService.findReviewWithComments(1L, pageRequest);

        // when
//        List<CommentResDTO> comments = dto.getComments();

        // then
//        assertThat(comments.size()).isEqualTo(2);
    }
}