package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.dto.login.JoinReqDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Admin extends Account{
    @OneToMany(mappedBy = "admin")
    private List<Restaurant> restaurants;

    public Admin(JoinReqDTO req) {
        this.identifier = req.getId();
        this.password = req.getPassword();
        this.name = req.getName();
        this.nickName = req.getNickName();
        this.age = req.getAge();
        this.sex = req.getSex();
        this.job = req.getJob();
        this.favor = req.getFavor();
    }

    public Admin(String identifier, String pw, String name, String nickName, int age, boolean sex, Set<Role> roles) {
        this.identifier = identifier;
        this.password = pw;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.sex = sex;
        this.roles = roles;
    }
}
