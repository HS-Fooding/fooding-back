package hansung.ac.kr.fooding.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.ReviewPostDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity{
    @Id @GeneratedValue
    private long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    private String content;

    @OneToMany(mappedBy = "review")
    private List<Image> images;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private List<Comment> comments;

    private float star;

    private int viewCount;

    public void addImages(Image... image) {
        for (Image i : image) {
            images.add(i);
        }
    }

    public Review(ReviewPostDTO dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.star = dto.getStar();
        this.viewCount = 0;
    }
}
