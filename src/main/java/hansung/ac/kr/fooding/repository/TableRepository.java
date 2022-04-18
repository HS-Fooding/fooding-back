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

    @Query(value = "SELECT * FROM _table as t "+
            "INNER JOIN reservation as reserv ON reserv.tableasd = t.id " +
            "WHERE reserv.restaurant_id = :restId " +
            "IN ((t.min_people >= :num OR :num >= t.max_people) OR )", nativeQuery = true)
    List<Table> findUnavailByRestIdWithDateAndTime(Long restId, int num, String date, String time);
    //restId -> 날짜랑 시간을 받자나. restId 일치하는 예약에 대해서, date가 같고, time이 같을 경우, 또는 테이블의 num이 min max에 포함되지 않을 때
    //의 테이블들의 리스트를 반환하게 만들고싶은데.
    // Member.teamId
}
