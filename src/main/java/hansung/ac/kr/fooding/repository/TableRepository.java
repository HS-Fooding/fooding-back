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

    @Query(value = "SELECT * FROM _table as t " +
            "INNER JOIN structure as s ON t.id = s.id " +
            "INNER JOIN floor as f ON f.id = s.floor_id " +
            "INNER JOIN restaurant as r ON f.restaurant_id = r.id " +
            "WHERE r.id = :restId AND t.min_people <= :num AND :num <= t.max_people", nativeQuery = true)
    List<Table> findByRestIdWithNum(Long restId, int num);


//    @Query(value = "SELECT * FROM _table as t "+
//            "INNER JOIN reservation as reserv ON reserv.tableasd = t.id " +
//            "WHERE reserv.restaurant_id = :restId AND t.min_people >= :num OR :num >= t.max_people AND "+
//            "reserv.reserve_date = :date AND reserv.reserve_time = :time", nativeQuery = true)

    @Query(value = "SELECT * FROM _table as t " +
            "INNER JOIN structure as s ON t.id = s.id " +
            "INNER JOIN floor as f ON f.id = s.floor_id " +
            "INNER JOIN restaurant as rest ON f.restaurant_id = rest.id " +
            "LEFT JOIN reservation as reserv ON reserv.tableasd = t.id " +
            "WHERE ((t.min_people > :num OR :num > t.max_people) " +
            "OR (reserv.reserve_date = :date AND reserv.reserve_time = :time)) "+
            "IN (rest.id = :restId)", nativeQuery = true)
    List<Table> findUnavailByRestIdWithDateAndTime(Long restId, int num, String date, String time);
}
