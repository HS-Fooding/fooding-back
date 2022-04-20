package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);

    @Query("select r from Restaurant r where r.location.region2Depth = :region2Depth")
    Page<Restaurant> findAllByRegion2Depth(@Param("region2Depth") String region2Depth, Pageable pageable);

    @Query("select r from Restaurant r where r.location.region3Depth = :region3Depth")
    Page<Restaurant> findAllByRegion3Depth(@Param("region3Depth") String region3Depth, Pageable pageable);

    @Query("select r from Restaurant r where r.name like %:name%")
    Page<Restaurant> findAllByName(@Param("name") String name, Pageable pageable);

    @Query("select r from Restaurant r join r.menus m where m.name like %:menu%")
    Page<Restaurant> findAllByMenu(@Param("menu") String menu, Pageable pageable);


    @EntityGraph(attributePaths = {"reservations", "reservations.table"})
    Restaurant findResById(Long id);

    @Query("select r from Restaurant r")
    Slice<Restaurant> findAllRest(Pageable pageable);

    @EntityGraph(attributePaths = {"reviews"})
    Optional<Restaurant> findRestById(Long restId);


//    @Query("select r from Restaurant r where r.id = :restId")
//    Optional<Restaurant> getReviewsOnly(Long restId);
}
