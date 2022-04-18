package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.InitData;
import hansung.ac.kr.fooding.domain.structure.Table;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TableRepositoryTest {
    @Autowired
    TableRepository tableRepository;
    @Autowired
    InitData initData;
    @Test
    void findTableByTableNum() {
        List<Table> table = tableRepository.findTableByTableNum("2", 2L);
        Assertions.assertThat(table.get(0).getTableNum()).isEqualTo("2");
        System.out.println(table.get(0).toString());
        System.out.println(table.get(1).toString());
    }

    @Test
    public void findTableByRestIdWithNumAndDateAndTimeTest() throws Exception {
        //given
        Long restId = 1L;
        int num =2;
        String date = "1997-06-05";
        String time = "10:00";
        //when
        List<Table> tables= tableRepository.findUnavailByRestIdWithDateAndTime(restId, num, date, time);
        List<Table> tables1= tableRepository.findUnavailByRestIdWithDateAndTime(restId, 100, date, "123");
        //then
        System.out.println("############"+tables);
        System.out.println("############"+tables1);
        System.out.println("############"+tableRepository.findAll());
    }
}