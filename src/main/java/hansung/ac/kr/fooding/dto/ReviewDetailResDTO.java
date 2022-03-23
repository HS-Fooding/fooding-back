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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDetailResDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
    private List<String> images = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private String createdDate;
    private float star;
    private int viewCount;

    public ReviewDetailResDTO(Review review) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.author = review.getAuthor().getName();
        this.content = review.getContent();
        if(review.getImages() != null) {
            for (Image image : review.getImages()) {
                this.images.add(image.getPath());
            }
        }
        if(review.getComments() != null) {
            for (Comment comment : comments) {
                this.comments = review.getComments();
            }
        }
        this.star = review.getStar();
        this.viewCount = review.getViewCount();
        this.createdDate = review.getCreatedDate().toString();
    }
}
