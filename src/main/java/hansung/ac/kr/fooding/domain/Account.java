package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String identifier;
    protected String password;
    protected String name;
    protected String nickName;
    protected int age;
    protected boolean sex;

    @Enumerated(value = EnumType.STRING)
    protected Job job;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    @CollectionTable(name = "favor", joinColumns = @JoinColumn(name = "member_id"))
    protected List<Favor> favor;

    /*@Enumerated(value = EnumType.STRING)
    private Role role;*/

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Set<Role> roles = new HashSet<>();

    public void setRoles(List<Role> roles) {
        this.roles = new HashSet<>(roles);
    }

}
