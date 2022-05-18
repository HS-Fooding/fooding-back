package hansung.ac.kr.fooding.dto.searchCondition;

import com.sun.istack.NotNull;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchCond {
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    private Integer age;
    private Boolean sex;
    private List<Job> job = new ArrayList<>();
//    private Job job;
    private List<Favor> favor = new ArrayList<>();
    private Integer reserveNum;
    private String startTime;
    private String endTime;
    private Boolean isCar;
}
