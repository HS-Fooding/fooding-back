package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
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

@Api(tags = {SwaggerConfig.API_REVIEW})
@RestController
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final SecurityService securityHandler;

    @ApiOperation(value = "리뷰 리스트 불러오기")
    @GetMapping("/review")
    public Page<ReviewSimpleResDTO> getReviews(Pageable pageable) {
        Page<Review> page = reviewRepository.findAll(pageable);
        Page<ReviewSimpleResDTO> result = page.map(m -> new ReviewSimpleResDTO(m));

        return result;
    }
    
    @ApiOperation(value = "리뷰 작성하기")
    @Transactional
    @PostMapping("/review")
    public ResponseEntity<ReviewPostDTO> postReview(
                                    @RequestPart(value = "review") ReviewPostDTO reviewPostDTO,
                                     @RequestPart(value = "image", required = false) List<MultipartFile> images) {

        // 현재 로그인한 사용자의 identifier
        String userIdentifier = securityHandler.getUserIdentifier();

        // TODO : Redirection 해야 함

        ReviewPostDTO result = reviewService.postReview(userIdentifier, reviewPostDTO, images);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "특정 리뷰 가져오기")
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDetailResDTO> getReview(@PathVariable Long reviewId) {

        ReviewDetailResDTO result = reviewService.findReviewWithComments(reviewId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
