package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.dto.JoinReqDTO;
import lombok.*;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.util.List;

@Entity
@Getter @Setter
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

    /*public Member(String identifier, String password, String nickName) {
        this.identifier = identifier;
        this.password = password;
        this.nickName = nickName;
        HashSet aa = (new HashSet<Role>());
        aa.add(new Role("ROLE_USER"));
        setRoles(aa);
    }*/
}
