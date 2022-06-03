package hansung.ac.kr.fooding.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@Getter
@NoArgsConstructor
public class Booker {
    private Long member_id;
    private String name;
    private String nickName;
    private String phoneNum;

    private Booker(Member member){
        member_id = member.getId();
        name = member.getName();
        nickName = member.getNickName();
        phoneNum = "00";
    }

    public static Booker from(Member member) {
        return new Booker(member);
    }
}
