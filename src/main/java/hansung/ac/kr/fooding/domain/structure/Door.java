package hansung.ac.kr.fooding.domain.structure;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Door")
public class Door extends Structure{
}
