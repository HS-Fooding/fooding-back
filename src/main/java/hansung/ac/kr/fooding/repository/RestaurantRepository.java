package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Reservation;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.domain.WorkHour;
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
import java.util.Set;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);

    @Query("select r.id from Restaurant r where r.location.region2Depth like %:region2Depth%")
    List<Long> findAllByRegion2Depth(@Param("region2Depth") String region2Depth);

    @Query("select r.id from Restaurant r where r.location.region3Depth like %:region3Depth%")
    List<Long> findAllByRegion3Depth(@Param("region3Depth") String region3Depth);

    @Query("select r.id from Restaurant r where r.name like %:name%")
    List<Long> findAllByName(@Param("name") String name);

    @Query("select r.id from Restaurant r join r.menus m where m.name like %:menu%")
    List<Long> findAllByMenu(@Param("menu") String menu);


    @EntityGraph(attributePaths = {"reservations", "reservations.table"})
    Restaurant findResById(Long id);

    @EntityGraph(attributePaths = {"floors"})
    Optional<Restaurant> findWithFloorsById(Long id);


    @Query("select r from Restaurant r")
    @EntityGraph(attributePaths = {"menus"})
    Slice<Restaurant> findAllRest(Pageable pageable);

    @Query("select r from Restaurant r join fetch r.reviews where r.id = :restId")
    Optional<Restaurant> findRestById(@Param("restId") Long restId);

    @Query("select r from Restaurant r where id in :ids")
    Slice<Restaurant> findAllByIds(@Param("ids") Set<Long> result, Pageable pageable);


    /*@Query("select r from Restaurant r where " +
            "pow(r.location.coordinate.x - :x, 2) + pow(r.location.coordinate.y - :y, 2) < pow(:r, 2)")*/
    @Query("select r from Restaurant r " +
            "where (6371 * acos(" +
                "cos(radians(:x)) * cos(radians(r.location.coordinate.x)) * " +
                "cos(radians(r.location.coordinate.y) - radians(:y)) + " +
                "sin(radians(:x)) * sin(radians(r.location.coordinate.x))" +
            ")) <= :r")
    Slice<Restaurant> findRestByCoord(@Param("x") Double x, @Param("y") Double y, @Param("r") Double r, Pageable pageable);

    @Query("select r.weekdaysWorkHour from Restaurant r where r.id = :restId")
    WorkHour findWorkingHourById(@Param("restId") Long restId);


//    @Query("select r from Restaurant r where r.id = :restId")
//    Optional<Restaurant> getReviewsOnly(Long restId);
}
