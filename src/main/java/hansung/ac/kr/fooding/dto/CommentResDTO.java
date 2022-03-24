package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.Comment;
import hansung.ac.kr.fooding.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResDTO {
    private long id;
    private String content;
    private String nickName;
    private String createdAt;
    private String modifiedAt;

    public CommentResDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickName = comment.getUser().getNickName();
        this.createdAt = comment.getCreatedDate().toString();
        this.modifiedAt = comment.getLastModifiedDate().toString();
    }
}
