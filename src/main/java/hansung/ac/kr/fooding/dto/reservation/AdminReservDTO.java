package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.Reservation;
import lombok.Data;

@Data
public class AdminReservDTO {
    private String reservAt;
    private String tableNum;
    private String nickname;
    private int reservCount;
    private boolean isCar;

    private AdminReservDTO(Reservation reservation){
        reservAt = reservation.getReserveTime();
        tableNum = reservation.getTable().getTableNum();
        nickname = reservation.getMember().getName();
        reservCount = reservation.getReserveNum();
        isCar = reservation.isCar();
    }

    public static AdminReservDTO from(Reservation reservation){
        return new AdminReservDTO(reservation);
    }
}
