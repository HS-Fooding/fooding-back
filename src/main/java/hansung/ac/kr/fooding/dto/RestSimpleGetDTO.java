package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.Location;
import hansung.ac.kr.fooding.domain.Restaurant;
import lombok.Data;

@Data
public class RestSimpleGetDTO {
    private Long id;
    private String name;

    private RestSimpleGetDTO(Restaurant restaurant){
        id= restaurant.getId();
        name = restaurant.getName();
    }

    public static RestSimpleGetDTO from(Restaurant restaurant){
        return new RestSimpleGetDTO(restaurant);
    }
}
