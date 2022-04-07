package hansung.ac.kr.fooding.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.review.ReviewPostDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity{
    @Id @GeneratedValue
    private long id;

    private String title;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    private Account author;

    private String content;

    @OneToMany
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "comment_review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    private float star;

    private int viewCount;

    public void addImages(List<Image> imageList){
        images.addAll(imageList);
    }

    public Review(ReviewPostDTO dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.star = dto.getStar();
        this.viewCount = 0;
    }
}
