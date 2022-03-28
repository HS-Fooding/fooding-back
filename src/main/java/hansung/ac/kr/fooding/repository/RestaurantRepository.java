package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
