package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"comments"})
    @Query("select r from Review r where r.id = :reviewId")
    Optional<Review> findReviewWithComments(@Param("reviewId") Long reviewId);

    @Query("select r from Review r where r.id in :reviewIds")
    Slice<Review> findReviewsByIds(@Param("reviewIds") List<Long> reviewIds, Pageable pageable);
}
