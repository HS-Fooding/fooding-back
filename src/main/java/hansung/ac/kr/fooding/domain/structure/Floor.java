package hansung.ac.kr.fooding.domain.structure;

import hansung.ac.kr.fooding.domain.BaseEntity;
import hansung.ac.kr.fooding.domain.structure.Structure;

import javax.persistence.*;
import java.util.List;

@Entity
public class Floor extends BaseEntity {
    @Id @GeneratedValue
    private long id;
    private int floor;

    @OneToMany
    @JoinColumn(name = "floor")
    private List<Structure> structures;
}
