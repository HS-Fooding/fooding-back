package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.dto.login.JoinReqDTO;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends Account {
    @ManyToMany
    @JoinTable(name = "bookmark",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private Set<Restaurant> bookmark = new HashSet<>();

    public Member(JoinReqDTO req) {
        this.identifier = req.getId();
        this.password = req.getPassword();
        this.name = req.getName();
        this.nickName = req.getNickName();
        this.age = req.getAge();
        this.sex = req.getSex();
        this.job = req.getJob();
        this.favor = req.getFavor();
    }

    /*public Member(String identifier, String password, String nickName) {
        this.identifier = identifier;
        this.password = password;
        this.nickName = nickName;
        HashSet aa = (new HashSet<Role>());
        aa.add(new Role("ROLE_USER"));
        setRoles(aa);
    }*/
}
