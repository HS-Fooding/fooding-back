package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
