package hansung.ac.kr.fooding.dto.reservation;

import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Floor;
import hansung.ac.kr.fooding.domain.structure.Structure;
import hansung.ac.kr.fooding.domain.structure.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Data
public class AdminTableInfoDTO {
    private String open;
    private String close;
    private String date;
    private int maxUsageTime;
    private List<String> tableNums = new ArrayList<>();

    private AdminTableInfoDTO(Restaurant restaurant){
        Calendar cal = Calendar.getInstance();
        int isWeekdays = cal.get(Calendar.DAY_OF_WEEK);
        if(2<= isWeekdays && isWeekdays <= 6) {
            open = restaurant.getWeekdaysWorkHour().getOpen();
            close = restaurant.getWeekdaysWorkHour().getClose();
        } else {
            open = restaurant.getWeekendsWorkHour().getOpen();
            close = restaurant.getWeekendsWorkHour().getClose();
        }
        date = LocalDate.now().toString();
        maxUsageTime = (int)restaurant.getMaximumUsageTime();
        for(Floor floor : restaurant.getFloors()){
            for(Structure structure : floor.getStructures())
                if(structure instanceof Table)
                    tableNums.add(((Table) structure).getTableNum());
        }
    }
    public static AdminTableInfoDTO from(Restaurant restaurant){
        return new AdminTableInfoDTO(restaurant);
    }
}
