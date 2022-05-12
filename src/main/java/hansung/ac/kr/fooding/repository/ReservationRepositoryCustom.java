package hansung.ac.kr.fooding.repository;

import com.querydsl.core.Tuple;
import hansung.ac.kr.fooding.dto.searchCondition.SearchCond;

import java.util.List;

public interface ReservationRepositoryCustom {
    List<Tuple> search(Long restId, SearchCond condition);
}
