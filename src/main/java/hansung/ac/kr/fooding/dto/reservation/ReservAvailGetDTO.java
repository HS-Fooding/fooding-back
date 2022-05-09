package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.structure.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReservAvailGetDTO {
    List<Table> tables;
}
