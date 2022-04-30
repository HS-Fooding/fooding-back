package hansung.ac.kr.fooding.dto.reservation;

import lombok.Data;

@Data
public class ReservPostDTO {
    private String tableNum;
    private String reserveDate;
    private String reserveTime;
    private int reserveNum;
    private boolean isCar;
}
