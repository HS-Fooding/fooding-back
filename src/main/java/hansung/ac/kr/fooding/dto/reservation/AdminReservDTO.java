package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.Reservation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminReservDTO {
    private Long reservId;
    private String reservAt;
    private String tableNum;
    private String nickname;
    private String phoneNum;
    private int reservCount;
    private boolean isCar;

    private AdminReservDTO(Reservation reservation){
        reservId = reservation.getId();
        reservAt = reservation.getReserveTime();
        tableNum = reservation.getTable().getTableNum();
        nickname = reservation.getBooker().getNickName();
        phoneNum = reservation.getBooker().getPhoneNum();
        reservCount = reservation.getReserveNum();
        isCar = reservation.isCar();
    }

    public static AdminReservDTO from(Reservation reservation){
        return new AdminReservDTO(reservation);
    }
}
