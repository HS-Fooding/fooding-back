package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.structure.Table;
import lombok.Data;

@Data
public class TableDTO {
    private String tableNum;
    private int minPeople;
    private int maxPeople;
    private Boolean canReserv = true;

    public TableDTO(Table table) {
        tableNum = table.getTableNum();
        minPeople = table.getMinPeople();
        maxPeople = table.getMaxPeople();
    }

    public TableDTO(Table table, Boolean reservation) {
        tableNum = table.getTableNum();
        minPeople = table.getMinPeople();
        maxPeople = table.getMaxPeople();
        canReserv = reservation;
    }
}
