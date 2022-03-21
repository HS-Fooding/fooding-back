package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.image.Image;
import hansung.ac.kr.fooding.domain.image.MenuImage;

import javax.persistence.*;

@Entity
public class Menu extends BaseEntity{
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;
    private String description;
    private int price;
}
