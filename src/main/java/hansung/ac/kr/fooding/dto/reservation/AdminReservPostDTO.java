package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.Booker;
import lombok.Data;

@Data
public class AdminReservPostDTO extends ReservDTO {
    private Long reserv_id;
}
