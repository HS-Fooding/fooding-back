package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Location;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.WorkHour;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.image.Image;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RestInfoGetDTO {
    private Long id;
    private String name;
    private AccountDTO admin;
    private List<String> tel;
    private WorkHour weekdaysWorkHour;
    private WorkHour weekendsWorkHour;
    private String parkingInfo;
    private float maximumUsageTime;
    private String intro;
    private List<String> images = new ArrayList<String>();
    private Location location;
    private List<Favor> category;

    private RestInfoGetDTO(Restaurant restaurant){
        id = restaurant.getId();
        name = restaurant.getName();
        admin = AccountDTO.from(restaurant.getAdmin());
        tel = restaurant.getTel();
        weekdaysWorkHour = restaurant.getWeekdaysWorkHour();
        weekendsWorkHour = restaurant.getWeekendsWorkHour();
        parkingInfo = restaurant.getParkingInfo();
        maximumUsageTime = restaurant.getMaximumUsageTime();
        intro = restaurant.getIntro();
        if(restaurant.getLocation() != null)
        location = restaurant.getLocation();
        if(restaurant.getCategory() != null)
        category = restaurant.getCategory();
        if(!restaurant.getImages().isEmpty())
            for(Image image : restaurant.getImages())
                images.add(image.getPath());
    }

    public static RestInfoGetDTO from(Restaurant restaurant){
        return new RestInfoGetDTO(restaurant);
    }

    @Override
    public String toString() {
        return "RestInfoGetDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", admin=" + admin +
                ", tel=" + tel +
                ", weekdaysWorkHour=" + weekdaysWorkHour +
                ", weekendsWorkHour=" + weekendsWorkHour +
                ", intro='" + intro + '\'' +
                ", location=" + location +
                ", category=" + category +
                '}';
    }
}
