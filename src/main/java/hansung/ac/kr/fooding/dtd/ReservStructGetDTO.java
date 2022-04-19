package hansung.ac.kr.fooding.dtd;

import hansung.ac.kr.fooding.dto.ReservFloorDTO;
import hansung.ac.kr.fooding.dto.TableDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReservStructGetDTO {
    List<ReservFloorDTO> floors;
}
