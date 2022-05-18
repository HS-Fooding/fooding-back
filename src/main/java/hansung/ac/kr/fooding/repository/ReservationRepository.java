package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Reservation;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

public interface ReservationRepository extends JpaRepository<Reservation, Long> , ReservationRepositoryCustom {
    List<Reservation> findByReserveDate(String date);

    @Query("select r from Reservation r where r.booker.member_id = :memberId")
    List<Reservation> findByBookerId(@Param("memberId") Long memberId);
}
