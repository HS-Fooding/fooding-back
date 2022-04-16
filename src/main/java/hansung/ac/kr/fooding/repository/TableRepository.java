package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.structure.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TableRepository extends JpaRepository<Table, Long> {
    @Query(value = "SELECT * FROM _table as t " +
            "INNER JOIN structure as s ON t.id = s.id " +
            "INNER JOIN floor as f ON f.id = s.floor_id " +
            "INNER JOIN restaurant as r ON f.restaurant_id = r.id " +
            "where r.id = :restId and t.table_num = :tableNum", nativeQuery = true)
    List<Table> findTableByTableNum(String tableNum, Long restId);
}
