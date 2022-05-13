package hansung.ac.kr.fooding.domain.image;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hansung.ac.kr.fooding.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@MappedSuperclass
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Getter @Setter
@NoArgsConstructor
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
