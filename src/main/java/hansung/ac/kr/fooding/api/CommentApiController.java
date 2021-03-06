package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.dto.comment.CommentPostDTO;
import hansung.ac.kr.fooding.dto.comment.CommentResDTO;
import hansung.ac.kr.fooding.service.CommentService;
import hansung.ac.kr.fooding.service.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@Api(tags = {SwaggerConfig.API_COMMENT})
@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;
    private final SecurityService securityService;

    @ApiOperation(value = "댓글 작성")
    @PostMapping("/restaurant/{id}/review/{reviewId}/comment")
    public ResponseEntity postComment(
            @PathVariable Long id,
            @PathVariable Long reviewId,
            @RequestBody CommentPostDTO dto) {

        CommentResDTO commentResDTO;
        try {
            Account account = securityService.getAccount();
            if (account == null) throw new SecurityException("Not Logged in");
            commentResDTO = commentService.postComment(reviewId, account, dto);
        } catch (Exception e) {
            return new ResponseEntity<>("Fooding-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(commentResDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 수정")
    @PatchMapping("/restaurant/{id}/review/{reviewId}/comment/{commentId}")
    public ResponseEntity updateComment(
            @PathVariable Long id,
            @PathVariable Long reviewId,
            @PathVariable Long commentId,
            @RequestBody CommentPostDTO dto) {
        try {
            commentService.updateComment(reviewId, commentId, dto);
        } catch (Exception e) {
            return new ResponseEntity("Fooding-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/restaurant/{id}/review/{reviewId}/comment/{commentId}")
    public ResponseEntity deleteComment(
            @PathVariable Long id,
            @PathVariable Long reviewId,
            @PathVariable Long commentId) {
        try {
            commentService.deleteComment(reviewId, commentId);
        } catch (Exception e) {
            return new ResponseEntity("Fooding-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }
}
