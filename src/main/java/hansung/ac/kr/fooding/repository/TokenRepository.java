package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t WHERE t.string in :strings")
    List<Token> getTokens(List<String> strings);

    Token findByString(String string);

    @Query("SELECT t FROM Token t WHERE t.string LIKE %:input%")
    List<Token> findByStringContains(String input);
}
