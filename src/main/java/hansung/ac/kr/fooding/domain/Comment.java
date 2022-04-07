package hansung.ac.kr.fooding.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hansung.ac.kr.fooding.dto.CommentPostDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    ///////////////////////
    @ManyToOne(fetch = LAZY)
    private Account user;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review comment_review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    private List<Comment> child = new ArrayList<>();

    public Comment(Review review, Account account, CommentPostDTO commentPostDTO) {
        this.content = commentPostDTO.getContent();
        this.user = account;
        this.parent = this;
        addReview(review);
    }
    public void setParent(Comment comment) {
        this.parent = comment;
    }

    // ## 양방향 연관관계에 대한 편의 메서드 ## //
    public void addReview(Review review) {
        this.comment_review = review;
        review.getComments().add(this);
    }

    public void addChildCategory(Comment child) {
        this.child.add(child);
        child.setParent(this);
    }
}
