package hansung.ac.kr.fooding.dto.mypage;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyInfoDTO {
    private String name;
    private String nickName;
    private int age;
    private boolean sex;
    private Job job;
    private List<Favor> favor = new ArrayList<>();

    public MyInfoDTO(Account account) {
        name = account.getName();
        nickName = account.getNickName();
        age = account.getAge();
        sex = account.isSex();
        job = account.getJob();
        favor = account.getFavor();
    }
}
