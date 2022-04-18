package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.structure.Table;
import lombok.Data;

import java.util.List;

@Data
public class ReservAvailGetDTO {
    List<Table> tables;
}
