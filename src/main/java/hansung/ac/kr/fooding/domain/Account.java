package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import hansung.ac.kr.fooding.domain.enumeration.Role;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account extends BaseEntity{
    @Id @GeneratedValue
    private long id;
    private String identifier;
    private String password;
    private String name;
    private String nickName;
    private LocalDate birthDate;
    private boolean sex;
    @Enumerated(value = EnumType.STRING)
    private Job job;
    @Enumerated(value = EnumType.STRING)
    private Favor favor;
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
