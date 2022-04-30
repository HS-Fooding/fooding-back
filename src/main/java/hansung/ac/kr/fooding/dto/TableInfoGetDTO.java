package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Table;
import lombok.Data;

@Data
public class TableInfoGetDTO {
    private Long restaurantID;
    private String tableNum;
    private int minPeople;
    private int maxPeople;

    public TableInfoGetDTO(Restaurant restaurant, Table table) {
        restaurantID = getRestaurantID();
        tableNum = table.getTableNum();
        minPeople = table.getMinPeople();
        maxPeople = table.getMaxPeople();
    }

    public static TableInfoGetDTO from(Restaurant restaurant, Table table){
        return new TableInfoGetDTO(restaurant, table);
    }
}
