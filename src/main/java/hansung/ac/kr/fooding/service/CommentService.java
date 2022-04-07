package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Comment;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.dto.comment.CommentPostDTO;
import hansung.ac.kr.fooding.repository.CommentRepository;
import hansung.ac.kr.fooding.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    // 댓글 등록
    @Transactional
    public void postComment(Long reviewId, Account account, CommentPostDTO dto) {

        // 누가, 어느 review id에, 어떤 comment를?
        Review review = reviewRepository.findById(reviewId).orElse(null);
        Comment comment = new Comment(review, account, dto);

        if(dto.getParent() != null) {
            comment.setParent(commentRepository.findById(dto.getParent()).orElse(null));
        }
        commentRepository.save(comment);
    }

    public void deleteComment(Long reviewId, Long commentId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if(optionalReview.isEmpty()) throw new IllegalStateException("Review Not Found");
        if(optionalComment.isEmpty()) throw new IllegalStateException("Comment Not Found");

        Review review = optionalReview.get();
        Comment comment = optionalComment.get();

        review.getComments().remove(comment);
    }

    public void updateComment(Long reviewId, Long commentId, CommentPostDTO dto) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if(optionalReview.isEmpty()) throw new IllegalStateException("Review Not Found");
        if(optionalComment.isEmpty()) throw new IllegalStateException("Comment Not Found");

        Review review = optionalReview.get();
        Comment comment = optionalComment.get();

        comment.setContent(dto.getContent());
    }
}
