package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.ReviewDetailResDTO;
import hansung.ac.kr.fooding.dto.ReviewPostDTO;
import hansung.ac.kr.fooding.handler.ImageHandler;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.ImageRepository;
import hansung.ac.kr.fooding.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public ReviewPostDTO postReview(String id, ReviewPostDTO reviewPostDto, List<MultipartFile> multipartImages) {

        List<Image> images = ImageHandler.upload(multipartImages);
        Review review = new Review(reviewPostDto);
        review.setAuthor(accountRepository.findByIdentifier(id));

        reviewRepository.save(review);
        imageRepository.saveImages(images);
        review.addImages(images);

        return reviewPostDto;
    }

    @Transactional
    public ReviewDetailResDTO findReviewWithComments(Long reviewId) {
        Review review = reviewRepository.findReviewWithComments(reviewId).orElse(null);

        review.setViewCount(review.getViewCount() + 1);

        ReviewDetailResDTO reviewDetailResDTO = new ReviewDetailResDTO(review);

        return reviewDetailResDTO;
    }
}
