package hansung.ac.kr.fooding.dtd;

import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.dto.FloorDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StructGetDTO {
    List<FloorDTO> floors;
}
