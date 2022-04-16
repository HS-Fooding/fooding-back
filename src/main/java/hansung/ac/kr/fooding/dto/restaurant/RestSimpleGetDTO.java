package hansung.ac.kr.fooding.dto.restaurant;

import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.image.Image;
import lombok.Data;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class RestSimpleGetDTO {
    private Long id;
    private String name;
    private Image image;
    private List<Favor> category;
    private int viewCount;
    private int reviewCount;
    private float avgScore;

    private RestSimpleGetDTO(Restaurant restaurant) {
        id = restaurant.getId();
        name = restaurant.getName();
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

    public static RestSimpleGetDTO from(Restaurant restaurant) {
        return new RestSimpleGetDTO(restaurant);
    }
}
