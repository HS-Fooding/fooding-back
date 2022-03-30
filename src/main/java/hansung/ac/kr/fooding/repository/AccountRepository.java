package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByIdentifier(String identifier);
    boolean existsByNickName(String nickName);
    boolean existsByIdentifier(String id);
}
