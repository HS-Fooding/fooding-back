package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.dto.searchCondition.RestaurantSearchCondition;

import java.util.List;


public interface RestaurantRepositoryCustom {
    List<Restaurant> search(RestaurantSearchCondition condition);
}
