package hansung.ac.kr.fooding.dto.review;

import hansung.ac.kr.fooding.domain.Comment;
import hansung.ac.kr.fooding.domain.Review;
import hansung.ac.kr.fooding.dto.image.ImageResDTO;
import hansung.ac.kr.fooding.dto.comment.CommentResDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDetailResDTO {
    private Long id;
    private String title;
    private String nickName;
    private String content;
    private List<ImageResDTO> images;
    private Slice<CommentResDTO> comments;
    private String createdDate;
    private float star;
    private int viewCount;

    public ReviewDetailResDTO(Review review, Slice<Comment> comments) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.nickName = review.getAuthor().getNickName();
        this.content = review.getContent();
        if(review.getImages() != null) {
            this.images = review.getImages().stream()
                    .map(m -> new ImageResDTO(m))
                    .collect(Collectors.toList());
        }
       /* if(review.getComments() != null) {
            this.comments = review.getComments().stream()
                    .map(m -> new CommentResDTO(m))
                    .collect(Collectors.toList());
        }*/
        this.comments = comments.map(CommentResDTO::new);
        this.star = review.getStar();
        this.viewCount = review.getViewCount();
        this.createdDate = review.getCreatedDate().toString();
    }
}
