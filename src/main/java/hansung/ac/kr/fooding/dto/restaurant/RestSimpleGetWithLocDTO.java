package hansung.ac.kr.fooding.dto.restaurant;

import hansung.ac.kr.fooding.domain.*;
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
    private boolean isBookmarked = false;

    public RestSimpleGetWithLocDTO(Restaurant restaurant) {
        id = restaurant.getId();
        name = restaurant.getName();
        location = restaurant.getLocation();
        // 이미지는 첫 번째로 나온 대표 메뉴로 보냄, 대표 메뉴가 없으면 첫 번째 메뉴로 보냄
        if (restaurant.getMenus().size() == 0) {
            if (restaurant.getImages().size() != 0)
                image = restaurant.getImages().get(0);
        }
        else if (restaurant.getMenus().stream().filter(Menu::isRepresentative).collect(Collectors.toList()).size() == 0)
            image = restaurant.getMenus().get(0).getImage();
        else
            image = restaurant.getMenus().stream().filter(Menu::isRepresentative).collect(Collectors.toList()).get(0).getImage();
        category = restaurant.getCategory();
        viewCount = restaurant.getViewCount();
        DoubleSummaryStatistics statistics = restaurant.getReviews().stream()
                .collect(Collectors.summarizingDouble(Review::getStar));
        reviewCount = (int) statistics.getCount();
        avgScore = (float) statistics.getAverage();
    }

    public RestSimpleGetWithLocDTO(Restaurant restaurant, List<Restaurant> bookmarkList) {
        id = restaurant.getId();
        name = restaurant.getName();
        location = restaurant.getLocation();
        // 이미지는 첫 번째로 나온 대표 메뉴로 보냄, 대표 메뉴가 없으면 첫 번째 메뉴로 보냄
        if (restaurant.getMenus().size() == 0) {
            if (restaurant.getImages().size() != 0)
                image = restaurant.getImages().get(0);
        }
        else if (restaurant.getMenus().stream().filter(Menu::isRepresentative).collect(Collectors.toList()).size() == 0)
            image = restaurant.getMenus().get(0).getImage();
        else
            image = restaurant.getMenus().stream().filter(Menu::isRepresentative).collect(Collectors.toList()).get(0).getImage();
        category = restaurant.getCategory();
        viewCount = restaurant.getViewCount();
        DoubleSummaryStatistics statistics = restaurant.getReviews().stream()
                .collect(Collectors.summarizingDouble(Review::getStar));
        reviewCount = (int) statistics.getCount();
        avgScore = (float) statistics.getAverage();
        isBookmarked = bookmarkList.contains(restaurant);
    }


    public static RestSimpleGetWithLocDTO from(Restaurant restaurant) {
        return new RestSimpleGetWithLocDTO(restaurant);
    }
    public static RestSimpleGetWithLocDTO from(Restaurant restaurant, List<Restaurant> bookmarkList) {
        return new RestSimpleGetWithLocDTO(restaurant, bookmarkList);
    }
}
