package hansung.ac.kr.fooding.domain.structure;

import hansung.ac.kr.fooding.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Structure extends BaseEntity {
    @Id @GeneratedValue
    private long id;
    private float x;
    private float y;
    private float angle;
    private float width;
    private float height;
    private String color;
}
