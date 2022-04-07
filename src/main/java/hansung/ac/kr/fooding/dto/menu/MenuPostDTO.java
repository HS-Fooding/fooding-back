package hansung.ac.kr.fooding.dto.menu;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuPostDTO {
    private String name;
    private String description;
    private int price;
    private boolean isRepresentative;

    public boolean getIsRepresentative() { return isRepresentative; }
    public void setIsRepresentative(boolean isRepresentative) { this.isRepresentative = isRepresentative;}
}
