package hansung.ac.kr.fooding.domain.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hansung.ac.kr.fooding.domain.BaseEntity;
import hansung.ac.kr.fooding.domain.Menu;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@MappedSuperclass
@Entity
@Getter @Setter
@NoArgsConstructor
public class Image extends BaseEntity {
    @Id @GeneratedValue
    private long id;
    private String path;

    public Image(String path){
        this.path = path;
    }

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "reviewImage")
//    private Review review;
//
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "restaurantImage")
//    private Restaurant restaurant;
//
//    @JsonIgnore
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menuImage")
//    private Menu menu;
}
