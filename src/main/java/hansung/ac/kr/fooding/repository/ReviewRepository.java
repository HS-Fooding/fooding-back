package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r " +
//            "join fetch r.comments " +
            "where r.id = :reviewId")
    Optional<Review> findReviewWithComments(@Param("reviewId") Long reviewId);
}
