package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.image.Image;

import javax.persistence.*;
import java.util.List;

@Entity
public class Menu extends BaseEntity{
    @Id
    @GeneratedValue
    private long id;
    private String name;
    /*@ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;*/

    @OneToOne(mappedBy = "restaurant")
    private Image image;

    private String description;
    private int price;
}
