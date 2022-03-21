package hansung.ac.kr.fooding.domain.structure;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Window")
public class Window extends Structure{
}
