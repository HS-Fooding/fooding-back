package hansung.ac.kr.fooding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPostDTO {
    private Long parent = 0L;
    private String content;
}
