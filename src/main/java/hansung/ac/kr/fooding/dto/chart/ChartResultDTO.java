package hansung.ac.kr.fooding.dto.chart;

import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChartResultDTO {
    Long reserveId;
    String reserveTime;
    Integer reserveNum;
    Long memberId;
    Boolean sex;
    Job job;
    List<Favor> favor = new ArrayList<>();

    public ChartResultDTO(ChartProjectionDTO dto) {
        reserveId = dto.getReserveId();
        reserveTime = dto.getReserveTime();
        reserveNum = dto.getReserveNum();
        if (dto.getMember() != null) {
            memberId = dto.getMember().getId();
            sex = dto.getMember().isSex();
            job = dto.getMember().getJob();
            favor = dto.getMember().getFavor();
        }
    }
}
