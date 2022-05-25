package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.dto.review.ReviewDetailResDTO;
import hansung.ac.kr.fooding.dto.review.ReviewPostDTO;
import hansung.ac.kr.fooding.dto.review.ReviewSimpleResDTO;
import hansung.ac.kr.fooding.service.ReviewService;
import hansung.ac.kr.fooding.service.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {SwaggerConfig.API_REVIEW})
@RestController
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;
    private final SecurityService securityService;

    @ApiOperation(value = "리뷰 리스트 불러오기")
    @GetMapping("/restaurant/{id}/review")
    public Slice<ReviewSimpleResDTO> getReviews(@PathVariable(value = "id") Long restId, Pageable pageable) {

        return reviewService.getReviews(restId, pageable);
    }

    @ApiOperation(value = "리뷰 작성하기")
    @PostMapping("/restaurant/{id}/review")
    public ResponseEntity postReview(
            @PathVariable(value = "id") Long restId,
            @RequestPart(value = "review") ReviewPostDTO reviewPostDTO,
            @RequestPart(value = "image", required = false) List<MultipartFile> images) {

        Long result;
        try {
            Member account = (Member) securityService.getAccount();
            if (account == null) throw new SecurityException("Not Logged in");
            result = reviewService.postReview(account, reviewPostDTO, images, restId);
        } catch (Exception e) {
            return new ResponseEntity<String>("Fooding-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "특정 리뷰 가져오기 (댓글도 함께 가져옴)")
    @GetMapping("/restaurant/{id}/review/{reviewId}")
    public ResponseEntity getReview(
            @PathVariable("id") Long restId,
            @PathVariable("reviewId") Long reviewId,
            Pageable pageable) {
        ReviewDetailResDTO result;
        try {
            result = reviewService.findReviewWithComments(reviewId, pageable);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>("Fooding-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "특정 리뷰 지우기 (댓글도 함께 지움)")
    @DeleteMapping("/restaurant/{id}/review/{reviewId}")
    public ResponseEntity deleteReview(
            @PathVariable Long id,
            @PathVariable Long reviewId) {
        try {
            reviewService.deleteReview(reviewId);
        } catch (IllegalStateException e) {
            return new ResponseEntity("Fooding-" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }
}
