package hansung.ac.kr.fooding.domain.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hansung.ac.kr.fooding.domain.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "_table")
@DiscriminatorValue("_table")
@Getter
@Setter
@NoArgsConstructor
public class Table extends Structure{
    private String tableNum;
    private int minPeople;
    private int maxPeople;
    private boolean available = true;

    @JsonIgnore
    @OneToMany(mappedBy = "table")
    private List<Reservation> reserved = new ArrayList<>();

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
