package hansung.ac.kr.fooding.domain;

import hansung.ac.kr.fooding.dto.JoinReqDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
        this.birthDate = LocalDate.parse(req.getBirthDate());
        this.sex = req.getSex();
        this.job = req.getJob();
        this.favor = req.getFavor();
    }
}
