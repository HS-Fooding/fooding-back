package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
