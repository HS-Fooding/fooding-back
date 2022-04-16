package hansung.ac.kr.fooding.dto.restaurant;

import hansung.ac.kr.fooding.domain.Location;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.image.Image;
import lombok.Data;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RestSimpleGetWithLocDTO {
    private Long id;
    private String name;
    private Location location;
    private Image image;
    private List<Favor> category;
    private int viewCount;
    private int reviewCount;
    private float avgScore;

    private RestSimpleGetWithLocDTO(Restaurant restaurant){
        id= restaurant.getId();
        name = restaurant.getName();
        location = restaurant.getLocation();
        if (restaurant.getImages().size() == 0)
            image = null;
        else
            image = restaurant.getImages().get(0);
        category = restaurant.getCategory();
        viewCount = restaurant.getViewCount();
        DoubleSummaryStatistics statistics = restaurant.getReviews().stream()
                .collect(Collectors.summarizingDouble(Review::getStar));
        reviewCount = (int) statistics.getCount();
        avgScore = (float) statistics.getAverage();
    }

    public static RestSimpleGetWithLocDTO from(Restaurant restaurant){
        return new RestSimpleGetWithLocDTO(restaurant);
    }
}
