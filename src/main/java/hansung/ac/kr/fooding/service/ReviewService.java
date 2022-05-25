package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Comment;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.review.ReviewDetailResDTO;
import hansung.ac.kr.fooding.dto.review.ReviewPostDTO;
import hansung.ac.kr.fooding.dto.review.ReviewSimpleResDTO;
import hansung.ac.kr.fooding.handler.ImageHandler;
import hansung.ac.kr.fooding.repository.CommentRepository;
import hansung.ac.kr.fooding.repository.ImageRepository;
import hansung.ac.kr.fooding.repository.RestaurantRepository;
import hansung.ac.kr.fooding.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final RestaurantRepository restaurantRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long postReview(Account account, ReviewPostDTO reviewPostDto, List<MultipartFile> multipartImages, Long restId) {

        Optional<Restaurant> optional = restaurantRepository.findById(restId);
        Restaurant restaurant = optional.orElseThrow(() -> new IllegalStateException("Restaurant Not Found"));

        Review review = new Review(reviewPostDto);
        List<Image> images = ImageHandler.upload(multipartImages);

        review.setAuthor(account);

        if (images != null) {
            imageRepository.saveImages(images);
            review.addImages(images);
        }

        restaurant.addReview(review);

        Review saved = reviewRepository.save(review);

        return saved.getId();
    }

    @Transactional
    public ReviewDetailResDTO findReviewWithComments(Long reviewId, Pageable pageable) {
        Optional<Review> optional = reviewRepository.findById(reviewId);
        Review review = optional.orElseThrow(() -> new IllegalStateException("Review Not Found"));
        List<Long> commentIds = review.getComments().stream().map(Comment::getId).collect(Collectors.toList());
        Slice<Comment> comments = commentRepository.findCommentsByIds(commentIds, pageable);

        for (Comment comment : comments) {
            System.out.println("comment!! = " + comment);
        }
//        System.out.println("NickName!!! = " + comments.getContent().get(0).getAuthor().getNickName());

        review.plusViewCount();
        return new ReviewDetailResDTO(review, comments);
    }

    public Slice<ReviewSimpleResDTO> getReviews(Long restId, Pageable pageable) {
        Optional<Restaurant> optional = restaurantRepository.findRestById(restId);
        Restaurant restaurant = optional.orElseThrow(() -> new IllegalStateException("Restaurant Not Found"));
        List<Long> reviewIds = restaurant.getReviews().stream().map(Review::getId).collect(Collectors.toList());
        Slice<Review> reviews = reviewRepository.findReviewsByIds(reviewIds, pageable);
        return reviews.map(ReviewSimpleResDTO::new);
    }

    public void deleteReview(Long reviewId) {
        Optional<Review> optional = reviewRepository.findById(reviewId);
        Review review = optional.orElseThrow(() -> new IllegalStateException("Review Not Found"));

        reviewRepository.delete(review);
    }
}
