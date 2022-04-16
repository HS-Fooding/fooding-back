package hansung.ac.kr.fooding.domain.structure;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "_table")
@DiscriminatorValue("_table")
@Getter
@NoArgsConstructor
public class Table extends Structure{
    private String tableNum;
    private int minPeople;
    private int maxPeople;
    private boolean available;

    @Override
    public String toString() {
        return "Table{" +
                "tableId=" + getId() +
                " tableNum='" + tableNum + '\'' +
                ", minPeople=" + minPeople +
                ", maxPeople=" + maxPeople +
                ", available=" + available +
                '}';
    }
}
