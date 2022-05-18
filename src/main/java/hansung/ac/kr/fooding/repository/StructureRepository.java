package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.domain.structure.Structure;
import hansung.ac.kr.fooding.domain.structure.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StructureRepository extends JpaRepository<Structure, Long> {

    @Query(value = "SELECT * FROM structure as s " +
            "INNER JOIN floor as f on f.id = s.floor_id " +
            "INNER JOIN restaurant as r on r.id = f.restaurant_id " +
            "WHERE r.id = restId", nativeQuery = true)
    List<Structure> findAllByRestId(Long restId);
}
