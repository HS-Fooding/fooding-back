package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.enumeration.AdminReservStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminReservUpdateDTO {
    private AdminReservStatus flag;
    private AdminReservPostDTO adminReservPostDTO;
}
