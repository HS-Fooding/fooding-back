package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByReserveDate(String date);
}
