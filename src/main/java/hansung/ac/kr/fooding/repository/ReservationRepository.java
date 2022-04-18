package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
