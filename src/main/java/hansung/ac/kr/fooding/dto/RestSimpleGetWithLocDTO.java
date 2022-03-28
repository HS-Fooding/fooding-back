package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.Location;
import hansung.ac.kr.fooding.domain.Restaurant;
import lombok.Data;

@Data
public class RestSimpleGetWithLocDTO {
    private Long id;
    private String name;
    private Location location;

    private RestSimpleGetWithLocDTO(Restaurant restaurant){
        id= restaurant.getId();
        name = restaurant.getName();
        location = restaurant.getLocation();
    }

    public static RestSimpleGetWithLocDTO from(Restaurant restaurant){
        return new RestSimpleGetWithLocDTO(restaurant);
    }
}
