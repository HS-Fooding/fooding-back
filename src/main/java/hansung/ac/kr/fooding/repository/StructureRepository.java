package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.structure.Structure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StructureRepository extends JpaRepository<Structure, Long> {
}
