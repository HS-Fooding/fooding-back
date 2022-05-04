package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.Booker;
import lombok.Data;

@Data
public class ReservDTO {
    protected String tableNum;
    protected String reserveDate;
    protected String reserveTime;
    protected int reserveNum;
    protected Booker booker;
    protected boolean isCar;
}
