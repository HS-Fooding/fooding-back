package hansung.ac.kr.fooding.dto.login;

import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Restaurant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginResDTO {
    private TokenResDTO token;
    private List<Long> restaurants = new ArrayList<>();

    public LoginResDTO(TokenResDTO tokenResDTO, Admin admin){
        token = tokenResDTO;
        if(!admin.getRestaurants().isEmpty())
            for(Restaurant restaurant : admin.getRestaurants()){
                restaurants.add(restaurant.getId());
            }
    }
}
