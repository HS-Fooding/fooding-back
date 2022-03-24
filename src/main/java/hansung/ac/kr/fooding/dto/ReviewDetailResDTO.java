package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.Comment;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.domain.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDetailResDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
    private List<ImageResDTO> images;
    private List<CommentResDTO> comments;
    private String createdDate;
    private float star;
    private int viewCount;

    public ReviewDetailResDTO(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.author = review.getAuthor().getName();
        this.content = review.getContent();
        if(review.getImages() != null) {
            this.images = review.getImages().stream()
                    .map(m -> new ImageResDTO(m))
                    .collect(Collectors.toList());
        }
        if(review.getComments() != null) {
            this.comments = review.getComments().stream()
                    .map(m -> new CommentResDTO(m))
                    .collect(Collectors.toList());
        }
        this.star = review.getStar();
        this.viewCount = review.getViewCount();
        this.createdDate = review.getCreatedDate().toString();
    }
}
