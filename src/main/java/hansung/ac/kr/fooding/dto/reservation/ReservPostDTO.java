package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.Booker;
import hansung.ac.kr.fooding.domain.enumeration.AdminReservStatus;
import lombok.Data;

@Data
public class ReservPostDTO {
    private String tableNum;
    private String reserveDate;
    private String reserveTime;
    private int reserveNum;
    private Booker booker;
    private boolean isCar;
}
