package hansung.ac.kr.fooding.dto.chart;

import hansung.ac.kr.fooding.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartProjectionDTO {
    Long reserveId;
    String reserveTime;
    Integer reserveNum;
    Member member;
}
