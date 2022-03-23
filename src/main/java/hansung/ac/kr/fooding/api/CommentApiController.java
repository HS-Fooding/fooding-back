package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.dto.CommentPostDTO;
import hansung.ac.kr.fooding.dto.ReviewDetailResDTO;
import hansung.ac.kr.fooding.repository.MemberRepository;
import hansung.ac.kr.fooding.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    @ApiOperation(value = "댓글 작성")
    @PostMapping("/review/{reviewId}/comment")
    public ResponseEntity postComment(@PathVariable(value = "reviewId") Long reviewId,
                                                          @RequestBody CommentPostDTO dto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByIdentifier(name);
        commentService.postComment(reviewId, member, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
