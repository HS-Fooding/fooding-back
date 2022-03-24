package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.dto.CommentPostDTO;
import hansung.ac.kr.fooding.repository.MemberRepository;
import hansung.ac.kr.fooding.service.CommentService;
import hansung.ac.kr.fooding.service.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {SwaggerConfig.API_COMMENT})
@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;
    private final MemberRepository memberRepository;
    private final SecurityService securityService;

    @ApiOperation(value = "댓글 작성")
    @PostMapping("/review/{reviewId}/comment")
    public ResponseEntity postComment(@PathVariable(value = "reviewId") Long reviewId,
                                      @RequestBody CommentPostDTO dto) {

        String identifier = securityService.getUserIdentifier();

        commentService.postComment(reviewId, identifier, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
