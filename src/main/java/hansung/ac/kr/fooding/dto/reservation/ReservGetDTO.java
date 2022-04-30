package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.dto.TableInfoGetDTO;
import hansung.ac.kr.fooding.dto.restaurant.RestInfoGetDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservGetDTO {
    RestInfoGetDTO restaurant;
    TableInfoGetDTO table;
}
