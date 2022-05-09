package hansung.ac.kr.fooding.dto.reservation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class AdminReservPostDTO extends ReservDTO {
    private Long reservId;
}
