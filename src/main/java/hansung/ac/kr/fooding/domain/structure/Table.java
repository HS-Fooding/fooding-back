package hansung.ac.kr.fooding.domain.structure;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "_table")
@DiscriminatorValue("_table")
@Getter
public class Table extends Structure{
    private String tableNum;
    private int minPeople;
    private int maxPeople;
    private boolean available;
}
