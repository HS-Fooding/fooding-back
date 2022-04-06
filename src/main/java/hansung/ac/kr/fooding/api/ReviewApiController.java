package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.dto.ReviewDetailResDTO;
import hansung.ac.kr.fooding.dto.ReviewPostDTO;
import hansung.ac.kr.fooding.dto.ReviewSimpleResDTO;
import hansung.ac.kr.fooding.repository.ReviewRepository;
import hansung.ac.kr.fooding.service.ReviewService;
import hansung.ac.kr.fooding.service.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {SwaggerConfig.API_REVIEW})
@RestController
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final SecurityService securityService;

    @ApiOperation(value = "리뷰 리스트 불러오기")
    @GetMapping("/restaurant/{id}/review")
    public List<ReviewSimpleResDTO> getReviews(@PathVariable(value = "id") Long restId, Pageable pageable) {
        List<Review> reviewsOnly = reviewService.getReviewsOnly(restId);

        return reviewsOnly.stream().map(m -> new ReviewSimpleResDTO(m)).collect(Collectors.toList());
    }

    @ApiOperation(value = "리뷰 작성하기")
    @PostMapping("/restaurant/{id}/review")
    public ResponseEntity<ReviewPostDTO> postReview(
            @PathVariable(value = "id") Long restId,
            @RequestPart(value = "review") ReviewPostDTO reviewPostDTO,
            @RequestPart(value = "image", required = false) List<MultipartFile> images) {

        Account account = securityService.getAccount();

        ReviewPostDTO result = reviewService.postReview(account, reviewPostDTO, images, restId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "특정 리뷰 가져오기")
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDetailResDTO> getReview(@PathVariable Long reviewId) {

        ReviewDetailResDTO result = reviewService.findReviewWithComments(reviewId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
