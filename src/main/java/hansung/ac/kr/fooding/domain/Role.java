package hansung.ac.kr.fooding.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "roles")
public class Role {
    @Id @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    private String roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<Account> accounts;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
