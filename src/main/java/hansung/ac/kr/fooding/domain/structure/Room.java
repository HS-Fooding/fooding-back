package hansung.ac.kr.fooding.domain.structure;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Room")
public class Room extends Structure{
    private int roomNum;
}
