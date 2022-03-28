package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
