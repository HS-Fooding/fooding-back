package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Reservation;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

public interface ReservationRepository extends JpaRepository<Reservation, Long> , ReservationRepositoryCustom {

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.reserveDate = :date AND r.restaurant.id = :restId")
    List<Reservation> findByReserveDate(Long restId, String date);

    @Query("select r from Reservation r where r.booker.member_id = :memberId")
    List<Reservation> findByBookerId(@Param("memberId") Long memberId);

    @Query("select r from Reservation r where r.reserveDate = :date and r.reserveTime = :time")
    @EntityGraph(attributePaths = {"table", "restaurant"})
    List<Reservation> findByDateAndTime(String date, String time);
}
