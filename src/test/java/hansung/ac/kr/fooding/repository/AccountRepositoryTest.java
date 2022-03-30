package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired AccountRepository accountRepository;

   /* @Test
    public void test() {
        Member member = new Member("identifier", "pass", "hello");
        Member save = memberRepository.save(member);

        Member member1 = memberRepository.findById(save.getId()).get();
        Account account = (Member) accountRepository.findById(member1.getId()).get();

        Assertions.assertThat(account instanceof Member).isTrue();
    }*/


}