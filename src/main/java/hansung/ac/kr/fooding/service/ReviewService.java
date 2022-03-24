package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.ReviewDetailResDTO;
import hansung.ac.kr.fooding.dto.ReviewPostDTO;
import hansung.ac.kr.fooding.handler.ImageHandler;
import hansung.ac.kr.fooding.repository.ImageRepository;
import hansung.ac.kr.fooding.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Transactional
    public ReviewPostDTO postReview(String id, ReviewPostDTO reviewPostDto, List<MultipartFile> images) {

        List<String> imagePaths = new ArrayList<>();
        if(images != null) {
            imagePaths = ImageHandler.upload(images);
        }


        Review review = new Review(reviewPostDto);
        review.setAuthor(memberRepository.findByIdentifier(id));

        reviewRepository.save(review);

        List<Image> savedImages = new ArrayList<>();
        if (imagePaths.size() > 0) {
            for (String imagePath : imagePaths) {
                Image savedImage = new Image();
                savedImage.setPath(imagePath);
                savedImage.setReview(review);
                savedImages.add(savedImage);
            }
        }
        review.setImages(savedImages);
        imageRepository.saveImages(savedImages);

        return reviewPostDto;
    }

    @Transactional
    public ReviewDetailResDTO findReviewWithComments(Long reviewId) {
        Review review = reviewRepository.findReviewWithComments(reviewId).orElse(null);
        if(review == null)
            System.out.println("fuck!!!!!!!!!!");
        review.setViewCount(review.getViewCount() + 1);

        ReviewDetailResDTO reviewDetailResDTO = new ReviewDetailResDTO(review);

        return reviewDetailResDTO;
    }
}
