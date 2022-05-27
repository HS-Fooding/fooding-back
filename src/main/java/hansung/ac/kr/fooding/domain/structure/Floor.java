package hansung.ac.kr.fooding.domain.structure;

import hansung.ac.kr.fooding.domain.BaseEntity;
import hansung.ac.kr.fooding.domain.structure.Structure;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Floor extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private int floor;

    @OneToMany
    @JoinColumn(name = "floor_id")
    private List<Structure> structures = new ArrayList<>();

    public void setFloor(int floor){
        this.floor = floor;
    }

    public void addStructure(Structure structure){
        structures.add(structure);
    }

    public void addStructures(List structures){
        if (structures == null) return;
        for(Object structure : structures) {
            this.addStructure((Structure) structure);
        }
    }
}
