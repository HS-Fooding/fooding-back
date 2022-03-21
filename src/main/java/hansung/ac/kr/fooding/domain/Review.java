package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.domain.image.ReviewImage;

import javax.persistence.*;
import java.util.List;

@Entity
public class Review extends BaseEntity{
    @Id @GeneratedValue
    private long id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
    private String content;
    @OneToMany(fetch = FetchType.LAZY)
    //@JoinColumn(name = "review_id")
    private List<Image> images;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private List<Comment> comments;
    private float star;
    private int viewCount;
}
