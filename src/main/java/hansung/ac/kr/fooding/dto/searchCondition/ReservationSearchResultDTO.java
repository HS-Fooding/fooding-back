package hansung.ac.kr.fooding.dto.searchCondition;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationSearchResultDTO {
    private String reserveAt;
    private int reservCount;
    private String nickName;
    private Boolean car;

}
