package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.dto.chart.ChartProjectionDTO;

import java.util.List;

public interface ReservationRepositoryCustom {
    List<ChartProjectionDTO> getChart(Long restId, String start, String end);
}
