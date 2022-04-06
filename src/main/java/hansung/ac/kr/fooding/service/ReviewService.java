package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.ReviewDetailResDTO;
import hansung.ac.kr.fooding.dto.ReviewPostDTO;
import hansung.ac.kr.fooding.handler.ImageHandler;
import hansung.ac.kr.fooding.repository.AccountRepository;
import hansung.ac.kr.fooding.repository.ImageRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public ReviewPostDTO postReview(Account account, ReviewPostDTO reviewPostDto, List<MultipartFile> multipartImages, Long restId) {

        Optional<Restaurant> optional = restaurantRepository.findById(restId);
        if(optional.isEmpty()) throw new IllegalStateException("Restaurant Not Found");

        Review review = new Review(reviewPostDto);
        List<Image> images = ImageHandler.upload(multipartImages);

        review.setAuthor(account);

        if(images != null) {
            imageRepository.saveImages(images);
            review.addImages(images);
        }

        Restaurant restaurant = optional.get();
        restaurant.addReview(review);

        reviewRepository.save(review);

        return reviewPostDto;
    }

    @Transactional
    public ReviewDetailResDTO findReviewWithComments(Long reviewId) {
        Review review = reviewRepository.findReviewWithComments(reviewId).orElse(null);

        review.setViewCount(review.getViewCount() + 1);

        ReviewDetailResDTO reviewDetailResDTO = new ReviewDetailResDTO(review);

        return reviewDetailResDTO;
    }

    public List<Review> getReviewsOnly(Long restId) {
        Optional<Restaurant> optional = restaurantRepository.findById(restId);
        if(optional.isEmpty()) throw new IllegalStateException("Restaurant Not Found");

        Restaurant restaurant = optional.get();

        return restaurant.getReviews();
    }
}
