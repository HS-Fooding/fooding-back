package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.dto.MenuPostDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Menu extends BaseEntity{
    @Id
    @GeneratedValue
    private long id;
    private String name;
    /*@ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;*/
    private String description;
    private int price;

    @OneToOne(mappedBy = "restaurant")
    private Image image;

    public Menu(MenuPostDTO menuPostDTO, Image image){
        this.name = menuPostDTO.getName();
        this.description = menuPostDTO.getDescription();
        this.price = menuPostDTO.getPrice();
        this.image = image;
    }
}
