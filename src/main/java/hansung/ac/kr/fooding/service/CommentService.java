package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Comment;
import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.dto.CommentPostDTO;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.CommentRepository;
import hansung.ac.kr.fooding.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
}
