package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.dto.JoinReqDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends Account {
    @ElementCollection
    @CollectionTable(name = "bookmark",
            joinColumns = @JoinColumn(name = "member_id"))
    private List<Restaurant> bookmark;

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

    public Member(String identifier, String password, String nickName) {
        this.identifier = identifier;
        this.password = password;
        this.nickName = nickName;
        HashSet aa = (new HashSet<Role>());
        aa.add(new Role("ROLE_USER"));
        setRoles(aa);
    }
}
