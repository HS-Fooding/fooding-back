package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Account extends BaseEntity{
    @Id @GeneratedValue
    protected long id;
    protected String identifier;
    protected String password;
    protected String name;
    protected String nickName;
    protected LocalDate birthDate;
    protected boolean sex;

    @Enumerated(value = EnumType.STRING)
    protected Job job;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    @CollectionTable(name="favor", joinColumns = @JoinColumn(name = "member_id"))
    protected List<Favor> favor;

    /*@Enumerated(value = EnumType.STRING)
    private Role role;*/

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "member_role",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
