package hansung.ac.kr.fooding.domain;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.util.List;

@Entity
public class Member extends Account{
    @ElementCollection
    @CollectionTable(name = "bookmark",
            joinColumns = @JoinColumn(name = "member_id"))
    private List<Restaurant> bookmark;
}
