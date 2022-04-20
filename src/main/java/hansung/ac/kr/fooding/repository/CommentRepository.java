package hansung.ac.kr.fooding.repository;

import hansung.ac.kr.fooding.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.id in :commentIds")
    Slice<Comment> findCommentsByIds(@Param("commentIds") List<Long> commentIds, Pageable pageable);
}
