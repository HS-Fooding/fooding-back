package hansung.ac.kr.fooding.domain;

import javax.persistence.*;

@Entity
public class Comment extends BaseEntity{
    @Id @GeneratedValue
    private long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "author")
    private Member user;
    @ManyToOne @JoinColumn(name = "parent_id")
    private Comment parent;
}
