package hansung.ac.kr.fooding.domain.structure;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Wall")
public class Wall extends Structure{
}
