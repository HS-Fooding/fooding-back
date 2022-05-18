//package hansung.ac.kr.fooding.manager;
//
//import hansung.ac.kr.fooding.domain.Account;
//import hansung.ac.kr.fooding.domain.Comment;
//import hansung.ac.kr.fooding.domain.Review;
//import hansung.ac.kr.fooding.dto.comment.CommentPostDTO;
//import hansung.ac.kr.fooding.exception.C_IllegalStateException;
//import hansung.ac.kr.fooding.exception.C_SecurityException;
//import hansung.ac.kr.fooding.repository.ReviewRepository;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@WithMockUser(username = "userId1")
//class CommentManagerTest {
//    @Autowired
//    CommentManager commentManager;
//    @Autowired
//    SecurityManager securityManager;
//    @Autowired
//    ReviewRepository reviewRepository;
//
//    @Test()
//    void getComment() throws C_IllegalStateException {
//        //given
//        Comment comment = commentManager.getComment(1L);
//
//        //when
//        assertThat(comment.getId()).isEqualTo(1L);
//        assertThrows(C_IllegalStateException.class, () -> {
//            Comment failedComment = commentManager.getComment(123L);
//        });
//    }
//
//    @Test()
//    void postComment() throws C_SecurityException, C_IllegalStateException {
//        //given
//        Account account = securityManager.getAccount();
//        Review review = reviewRepository.findById(1L).get();
//        CommentPostDTO dto = new CommentPostDTO(null, "testComment");
//
//        //when
//        Comment comment = commentManager.postComment(review, account, dto);
//        Comment findComment = commentManager.getComment(comment.getId());
//
//        //then
//        assertThat(comment.getId()).isEqualTo(findComment.getId());
//        assertThrows(C_IllegalStateException.class, () -> {
//            commentManager.getComment(123L);
//        });
//    }
//
//    @Test
//    void deleteComment() throws C_SecurityException {
//        Account account = securityManager.getAccount();
//        Review review = reviewRepository.findById(1L).get();
//        CommentPostDTO dto = new CommentPostDTO(null, "testComment");
//        Comment comment = commentManager.postComment(review, account, dto);
//        Long id = comment.getId();
//
//        //when
//        commentManager.deleteComment(review, comment);
//
//        //then
//        assertThrows(C_IllegalStateException.class, () -> {
//            commentManager.getComment(id);
//        });
//    }
//
//    @Test
//    void updateComment() throws C_IllegalStateException{
//        //given
//        Comment comment = commentManager.getComment(1L);
//        String content = "newContent";
//
//        //when
//        commentManager.updateComment(comment, new CommentPostDTO(null, content));
//
//        //then
//        assertThat(commentManager.getComment(1L).getContent()).isEqualTo(content);
//    }
//}