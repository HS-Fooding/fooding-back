package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Role;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import hansung.ac.kr.fooding.dto.JoinReqDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class MemberServiceTest {
    @Autowired MemberService memberService;


    @Test
    void join() throws X_NickNameAlreadyExistsException, X_IdAlreadyExistsException {
        Set<Role> roles = new HashSet<Role>();
        Role role = new Role("ROLE_ADMIN");
        JoinReqDTO joinReqDTO = new JoinReqDTO("testadmin", "testadminpw", "testadmin", "testadmin", true , 10, null, roles, Job.STUDENT);
        memberService.join(joinReqDTO);
    }
}