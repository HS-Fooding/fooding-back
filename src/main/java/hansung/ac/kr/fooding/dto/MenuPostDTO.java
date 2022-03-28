package hansung.ac.kr.fooding.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuPostDTO {
    private String name;
    private String description;
    private int price;
}
