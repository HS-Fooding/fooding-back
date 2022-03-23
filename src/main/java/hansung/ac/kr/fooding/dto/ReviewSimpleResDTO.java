package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSimpleResDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
    private List<ImageResDTO> image;
    private float star;
    private int viewCount;
    private String registerDate;

    public ReviewSimpleResDTO(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.author = review.getAuthor().getName();
        this.content = review.getContent();
        this.image = review.getImages().stream()
                .map(m -> new ImageResDTO(m)).collect(Collectors.toList());
        this.star = review.getStar();
        this.viewCount = review.getViewCount();
        this.registerDate = review.getCreatedDate().toString();
    }
}
