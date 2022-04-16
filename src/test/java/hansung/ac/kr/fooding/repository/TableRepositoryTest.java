package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.structure.Table;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TableRepositoryTest {
    @Autowired
    TableRepository tableRepository;
    @Test
    void findTableByTableNum() {
        List<Table> table = tableRepository.findTableByTableNum("2", 2L);
        Assertions.assertThat(table.get(0).getTableNum()).isEqualTo("2");
        System.out.println(table.get(0).toString());
        System.out.println(table.get(1).toString());
    }
}