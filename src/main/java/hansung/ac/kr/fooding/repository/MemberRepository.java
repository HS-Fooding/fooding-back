package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.dto.JoinReqDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
