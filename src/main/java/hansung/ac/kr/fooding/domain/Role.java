package hansung.ac.kr.fooding.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<Member> members;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
