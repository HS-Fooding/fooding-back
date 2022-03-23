package hansung.ac.kr.fooding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPostDTO {
    private String title;
    private String content;
    private float star;
}