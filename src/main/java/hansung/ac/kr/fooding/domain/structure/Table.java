package hansung.ac.kr.fooding.domain.structure;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "_table")
@DiscriminatorValue("_table")
public class Table extends Structure{
    private int tableNum;
    private int min;
    private int max;
    private boolean available;
}
