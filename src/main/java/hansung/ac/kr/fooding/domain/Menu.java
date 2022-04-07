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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    /*@ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;*/
    private String description;
    private int price;
    private boolean isRepresentative;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Menu(MenuPostDTO menuPostDTO, Image image){
        this.name = menuPostDTO.getName();
        this.description = menuPostDTO.getDescription();
        this.price = menuPostDTO.getPrice();
        this.isRepresentative = menuPostDTO.getIsRepresentative();
        this.image = image;
    }

    public void setImage(Image image){
        this.image = image;
    }
}
