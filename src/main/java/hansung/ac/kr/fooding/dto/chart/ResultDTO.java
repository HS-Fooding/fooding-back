package hansung.ac.kr.fooding.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDTO<T> {
    private T data;
    private T workingTime;
}
