package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.domain.structure.Structure;
import hansung.ac.kr.fooding.domain.structure.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AdminTableInfoDTO {
    private String open;
    private String close;
    private String date;
    private int maxUsageTime;
    private List<String> tableNums = new ArrayList<>();

    private AdminTableInfoDTO(Restaurant restaurant, String date){
        int isWeekdays;
        this.date = date;
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        isWeekdays = localDate.getDayOfWeek().getValue();
        if(isWeekdays < 6) {
            open = restaurant.getWeekdaysWorkHour().getOpen();
            close = restaurant.getWeekdaysWorkHour().getClose();
        } else {
            open = restaurant.getWeekendsWorkHour().getOpen();
            close = restaurant.getWeekendsWorkHour().getClose();
        }
        maxUsageTime = (int)restaurant.getMaximumUsageTime();
        for(Floor floor : restaurant.getFloors()){
            for(Structure structure : floor.getStructures())
                if(structure instanceof Table)
                    tableNums.add(((Table) structure).getTableNum());
        }
    }

    public static AdminTableInfoDTO from(Restaurant restaurant, String date){
        return new AdminTableInfoDTO(restaurant, date);
    }
}