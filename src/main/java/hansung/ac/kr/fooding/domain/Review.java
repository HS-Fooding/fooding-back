package hansung.ac.kr.fooding.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.review.ReviewPostDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    private Account author;

    @Column(length = 5000)
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

    public void plusViewCount() {
        viewCount = getViewCount() + 1;
    }

    public Review(ReviewPostDTO dto, Account account){
        title = dto.getTitle();
        content = dto.getContent();
        star = dto.getStar();
        viewCount = 0;
        author = account;
    }
}
