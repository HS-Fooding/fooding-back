package hansung.ac.kr.fooding.dto.chart;

import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.WorkHour;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartProjectionDTO {
    Long reserveId;
    String reserveTime;
    Integer reserveNum;
    WorkHour weekdays;
    WorkHour weekends;
    Member member;
}
