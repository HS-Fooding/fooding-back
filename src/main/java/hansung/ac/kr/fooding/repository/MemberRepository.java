package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Member;
import hansung.ac.kr.fooding.domain.enumeration.Favor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickName(String nickName);
    boolean existsByIdentifier(String id);
    Member findByIdentifier(String userId);

//    @Query("select f from Member m join fetch m.favor f where m.id in :membersId")
//    List<List<Favor>> findFavors(@Param("membersId") List<Long> membersId);
}
