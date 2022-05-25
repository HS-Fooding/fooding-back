package hansung.ac.kr.fooding.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hansung.ac.kr.fooding.dto.comment.CommentPostDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

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
    private Account author;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review comment_review;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    private List<Comment> child = new ArrayList<>();

    public Comment(Review review, Account account, CommentPostDTO commentPostDTO) {
        content = commentPostDTO.getContent();
        author = account;
        parent = this;
        addReview(review);
    }

    public Comment(Account account, CommentPostDTO commentPostDTO){
        content = commentPostDTO.getContent();
        author = account;
        parent = this;
    }

    public void setParent(Comment comment) {
        parent = comment;
    }

    // ## 양방향 연관관계에 대한 편의 메서드 ## //
    @Transactional
    public void addReview(Review review) {
        this.comment_review = review;
        review.getComments().add(this);
    }

    public void addChildCategory(Comment child) {
        this.child.add(child);
        child.setParent(this);
    }
}
