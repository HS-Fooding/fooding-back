package hansung.ac.kr.fooding.repository;

import com.querydsl.core.Tuple;
import hansung.ac.kr.fooding.dto.chart.ChartDTO;
import hansung.ac.kr.fooding.dto.searchCondition.SearchCond;

import java.util.List;

public interface ReservationRepositoryCustom {
    List<ChartDTO> search(Long restId, String start, String end);
}
