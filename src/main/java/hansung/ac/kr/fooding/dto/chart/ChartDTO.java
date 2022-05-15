package hansung.ac.kr.fooding.dto.chart;

import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class ChartDTO {
   Long id;
   Integer age;
   Boolean sex;
   Job job;
   String reserveTime;
   Integer reserveNum;
//   List<Favor> favor;
}
