package hansung.ac.kr.fooding.dto;

import hansung.ac.kr.fooding.domain.Menu;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuGetDTO {
    private long id;
    private String name;
    private String description;
    private int price;
    private boolean isRepresentative;
    private String image;

    private MenuGetDTO(Menu menu){
        id=menu.getId();
        name=menu.getName();
        description=menu.getDescription();
        price=menu.getPrice();
        isRepresentative=menu.isRepresentative();
        if(menu.getImage() != null)
            image = menu.getImage().getPath();
    }

    public static MenuGetDTO from(Menu menu){
        return new MenuGetDTO(menu);
    }
}
