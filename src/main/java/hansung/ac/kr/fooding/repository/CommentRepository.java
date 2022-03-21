package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
