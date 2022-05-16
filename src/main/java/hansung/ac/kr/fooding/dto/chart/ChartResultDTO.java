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
    String weekdayStart;
    String weekdayEnd;
    String weekendStart;
    String weekendEnd;
    Long memberId;
    Boolean sex;
    Integer age;
    Job job;
    List<Favor> favor = new ArrayList<>();

    public ChartResultDTO(ChartProjectionDTO dto) {
        reserveId = dto.getReserveId();
        reserveTime = dto.getReserveTime();
        reserveNum = dto.getReserveNum();
        weekdayStart = dto.getWeekdays().getOpen();
        weekdayEnd = dto.getWeekdays().getClose();
        weekendStart = dto.getWeekends().getOpen();
        weekendEnd = dto.getWeekends().getClose();

        if (dto.getMember() != null) {
            memberId = dto.getMember().getId();
            sex = dto.getMember().isSex();
            age = dto.getMember().getAge();
            job = dto.getMember().getJob();
            favor = dto.getMember().getFavor();
        }
    }
}
