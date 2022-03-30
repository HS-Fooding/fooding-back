package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Role;
import hansung.ac.kr.fooding.domain.enumeration.Job;
import hansung.ac.kr.fooding.dto.JoinReqDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NotRegisteredRole;
import hansung.ac.kr.fooding.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// #################### 테스트 진행하려면 auditing 주석처리하고 해야함.. ;;"
@SpringBootTest
@Transactional
@Rollback(value = false)
class AccountServiceTest {
    @Autowired
    AccountService memberService;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EntityManager em;

    @Test
    void join_ROLE_ADMIN()
            throws X_NickNameAlreadyExistsException, X_IdAlreadyExistsException, X_NotRegisteredRole {
        Set<Role> roles = new HashSet<>();
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        roles.add(role1);
        roles.add(role2);
        JoinReqDTO joinReqDTO = new JoinReqDTO("testadmin1", "testadmin2", "testadmin3", "testadmin4", true, 10, null, roles, Job.STUDENT);
        memberService.join(joinReqDTO);

        Account result = accountRepository.findByIdentifier("testadmin1");
        assertThat(result.getNickName()).isEqualTo("testadmin4");

        em.flush();
        em.clear();

        List<Account> accounts = em.createQuery("select a from Account a where type(a) = Admin", Account.class)
                .getResultList();

        assertThat(accounts.get(0).getNickName()).isEqualTo("testadmin4");
    }

    @Test
    void join_ROLE_USER()
            throws X_NickNameAlreadyExistsException, X_IdAlreadyExistsException, X_NotRegisteredRole {
        Set<Role> roles = new HashSet<>();
        Role role = new Role("ROLE_USER");
        roles.add(role);
        JoinReqDTO joinReqDTO = new JoinReqDTO("testuser1", "testuser2", "testuser4", "testuser4", true, 30, null, roles, Job.STUDENT);
        memberService.join(joinReqDTO);

        Account result = accountRepository.findByIdentifier("testuser1");
        assertThat(result.getNickName()).isEqualTo("testuser4");

        em.flush();
        em.clear();

        List<Account> accounts = em.createQuery("select a from Account a where a.identifier = :identifier", Account.class)
                .setParameter("identifier", "testuser1")
                .getResultList();
        assertThat(accounts.get(0).getNickName()).isEqualTo("testuser4");
    }

    @Test
    void join_inValid() {
        Set<Role> roles = new HashSet<Role>();
        Role role1 = new Role("HELLO_WORLD");
        roles.add(role1);
        JoinReqDTO joinReqDTO = new JoinReqDTO("testadmin", "testadminpw", "test", "testadmin", true, 10, null, roles, Job.STUDENT);
        assertThatThrownBy(() -> memberService.join(joinReqDTO)).isInstanceOf(X_NotRegisteredRole.class);
    }
}