package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByIdentifier(String identifier);
    boolean existsByNickName(String nickName);
    boolean existsByIdentifier(String id);

    @Query("select a from Account a where a.id = :id")
    Optional<Account> findByIdWithFavor(@Param("id") Long id);
}
