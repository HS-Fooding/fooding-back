package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickName(String nickName);
    boolean existsByIdentifier(String id);
}
