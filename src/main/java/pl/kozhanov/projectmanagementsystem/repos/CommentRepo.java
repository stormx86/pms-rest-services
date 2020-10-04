package pl.kozhanov.projectmanagementsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.projectmanagementsystem.domain.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    Comment getById(Integer id);
}
