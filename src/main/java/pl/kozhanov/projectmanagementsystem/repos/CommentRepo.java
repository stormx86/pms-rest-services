package pl.kozhanov.projectmanagementsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kozhanov.projectmanagementsystem.domain.Comment;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    Comment getById(Long id);

    @Query("SELECT c " +
            "FROM Comment c " +
            "JOIN c.project prj " +
            "WHERE prj.id = :projectId ")
    List<Comment> findAllByProjectId(@Param("projectId") Integer projectId);
}
