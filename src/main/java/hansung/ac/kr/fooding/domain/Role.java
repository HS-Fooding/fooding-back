package hansung.ac.kr.fooding.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<Account> accounts;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
