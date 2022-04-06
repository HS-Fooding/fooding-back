package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String restName);

//    @Query("select r from Restaurant r where r.id = :restId")
//    Optional<Restaurant> getReviewsOnly(Long restId);
}
