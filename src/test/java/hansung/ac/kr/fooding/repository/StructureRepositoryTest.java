package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.structure.Door;
import hansung.ac.kr.fooding.domain.structure.Structure;
import hansung.ac.kr.fooding.domain.structure.Table;
import hansung.ac.kr.fooding.domain.structure.Wall;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class StructureRepositoryTest {
    @Autowired StructureRepository structureRepository;

    @Test
    public void saveTest() {
        //given
        List<Structure> structures = new ArrayList<>();
        structures.add(new Wall());
        structures.add(new Door());
        structures.add(new Table());
        //when
        structureRepository.saveAll(structures);
        List<Structure> findStructures = structureRepository.findAll();
        //then
        assertThat(findStructures.isEmpty()).isFalse();
    }
}