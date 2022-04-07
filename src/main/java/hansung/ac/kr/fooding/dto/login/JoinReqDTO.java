package hansung.ac.kr.fooding.dto.login;

import hansung.ac.kr.fooding.domain.Role;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinReqDTO {
    String id;
    String password;
    String name;
    String nickName;
    Boolean sex;
    int age;
    List<Favor> favor;
    Set<Role> role;
    Job job;
}
