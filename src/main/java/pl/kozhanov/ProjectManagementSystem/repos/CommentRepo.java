package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.ProjectManagementSystem.domain.Comment;
import pl.kozhanov.ProjectManagementSystem.domain.Project;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    Comment getById(Integer id);
}
