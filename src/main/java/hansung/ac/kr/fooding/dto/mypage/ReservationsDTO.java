package hansung.ac.kr.fooding.dto.mypage;

import hansung.ac.kr.fooding.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ReservationsDTO {
    Long reserveId;
    String reserveDate;
    String reserveTime;

    Long restId;
    String restName;

    int reserveCount;
    Boolean isCar;
    String name;
    String nickName;

    public ReservationsDTO(Reservation reservation) {
        reserveId = reservation.getId();
        reserveDate = reservation.getReserveDate();
        reserveTime = reservation.getReserveTime();
        restId = reservation.getRestaurant().getId();
        restName = reservation.getRestaurant().getName();
        reserveCount = reservation.getReserveNum();
        isCar = reservation.isCar();
        name = reservation.getBooker().getName();
        nickName = reservation.getBooker().getNickName();
    }
}
