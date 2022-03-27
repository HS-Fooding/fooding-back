package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResDTO {
    private long id;
    private String content;
    private String nickName;
    private Long parent;
    private String createdAt;
    private String modifiedAt;

    public CommentResDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickName = comment.getUser().getNickName();
        Comment parentComment = Optional.ofNullable(comment.getParent()).orElse(null);
        if(parentComment != null)
            this.parent = Optional.ofNullable(parentComment.getId()).orElse(null);
        else
            this.parent = 0L;
        this.createdAt = comment.getCreatedDate().toString();
        this.modifiedAt = comment.getLastModifiedDate().toString();
    }
}
