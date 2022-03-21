package hansung.ac.kr.fooding.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Admin extends Account{
    @OneToMany(mappedBy = "admin")
    private List<Restaurant> restaurants;
}
