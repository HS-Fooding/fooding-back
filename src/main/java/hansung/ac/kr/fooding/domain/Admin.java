package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.dto.JoinReqDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

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
}
