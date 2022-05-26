package hansung.ac.kr.fooding.dto.restaurant;

import hansung.ac.kr.fooding.domain.Location;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.domain.WorkHour;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.account.AccountDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<String> images = new ArrayList<>();
    private Location location;
    private List<Favor> category;
    private int viewCount;
    private int reviewCount;
    private float avgScore;
    private boolean isBookmarked = false;


    private RestInfoGetDTO(Restaurant restaurant) {
        id = restaurant.getId();
        name = restaurant.getName();
        admin = AccountDTO.from(restaurant.getAdmin());
        tel = restaurant.getTel();
        weekdaysWorkHour = restaurant.getWeekdaysWorkHour();
        weekendsWorkHour = restaurant.getWeekendsWorkHour();
        parkingInfo = restaurant.getParkingInfo();
        maximumUsageTime = restaurant.getMaximumUsageTime();
        intro = restaurant.getIntro();
        if (restaurant.getLocation() != null)
            location = restaurant.getLocation();
        if (restaurant.getCategory() != null)
            category = restaurant.getCategory();
        if (!restaurant.getImages().isEmpty())
            for (Image image : restaurant.getImages())
                images.add(image.getPath());
        viewCount = restaurant.getViewCount();
        DoubleSummaryStatistics statistics = restaurant.getReviews().stream()
                .collect(Collectors.summarizingDouble(Review::getStar));
        reviewCount = (int) statistics.getCount();
        avgScore = (float) statistics.getAverage();
    }

    private RestInfoGetDTO(Restaurant restaurant, boolean bool) {
        id = restaurant.getId();
        name = restaurant.getName();
        admin = AccountDTO.from(restaurant.getAdmin());
        tel = restaurant.getTel();
        weekdaysWorkHour = restaurant.getWeekdaysWorkHour();
        weekendsWorkHour = restaurant.getWeekendsWorkHour();
        parkingInfo = restaurant.getParkingInfo();
        maximumUsageTime = restaurant.getMaximumUsageTime();
        intro = restaurant.getIntro();
        if (restaurant.getLocation() != null)
            location = restaurant.getLocation();
        if (restaurant.getCategory() != null)
            category = restaurant.getCategory();
        if (!restaurant.getImages().isEmpty())
            for (Image image : restaurant.getImages())
                images.add(image.getPath());
        viewCount = restaurant.getViewCount();
        DoubleSummaryStatistics statistics = restaurant.getReviews().stream()
                .collect(Collectors.summarizingDouble(Review::getStar));
        reviewCount = (int) statistics.getCount();
        avgScore = (float) statistics.getAverage();
        isBookmarked = bool;
    }

    public static RestInfoGetDTO from(Restaurant restaurant, Boolean bool) {
        return new RestInfoGetDTO(restaurant, bool);
    }

    public static RestInfoGetDTO from(Restaurant restaurant) {
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
