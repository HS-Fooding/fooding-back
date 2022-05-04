package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.enumeration.AdminReservStatus;
import lombok.Data;

@Data
public class AdminReservUpdateDTO {
    private AdminReservStatus flag;
    private AdminReservPostDTO adminReservPostDTO;
}
