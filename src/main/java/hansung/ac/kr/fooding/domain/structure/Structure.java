package hansung.ac.kr.fooding.domain.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hansung.ac.kr.fooding.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
public abstract class Structure extends BaseEntity {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int x;
    private int y;
    private int rotation;
    private int width;
    private int height;
    //private String color;

    @Override
    public String toString() {
        return "Structure{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", rotation=" + rotation +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
