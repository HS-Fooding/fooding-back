package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.Reservation;
import hansung.ac.kr.fooding.domain.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AdminReservGetDTO {
    private AdminTableInfoDTO tableInfo;
    private List<AdminReservDTO> reservations;

    public AdminReservGetDTO(AdminTableInfoDTO adminTableInfoDTO, List<AdminReservDTO> reservations){
        tableInfo = adminTableInfoDTO;
        this.reservations = reservations;
    }
}
