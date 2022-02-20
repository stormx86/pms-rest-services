package pl.kozhanov.projectmanagementsystem.service.mapping;

import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import pl.kozhanov.projectmanagementsystem.domain.Comment;
import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.dto.CommentDto;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;
import pl.kozhanov.projectmanagementsystem.repos.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import static pl.kozhanov.projectmanagementsystem.service.utils.DateTimeUtils.localDateTimeToStringConverter;

@Component
public class CommentToCommentDtoMapper extends DefaultCustomMapper<Comment, CommentDto> {

    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;

    public CommentToCommentDtoMapper(final ProjectRepo projectRepo,
                                     final UserRepo userRepo) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void mapAtoB(Comment comment, CommentDto commentDto, MappingContext context) {
        commentDto.setCommentId(comment.getId());
        commentDto.setProjectId(comment.getProject().getId());
        commentDto.setUserId(comment.getUser().getId());
        commentDto.setUsername(comment.getUser().getUsername());
        commentDto.setCreatedAt(localDateTimeToStringConverter(comment.getCreatedAt()));
    }

    @Override
    public void mapBtoA(CommentDto commentDto, Comment comment, MappingContext context) {
        comment.setCommentText(commentDto.getCommentText());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setProject(getProject(commentDto.getProjectId()));
        comment.setUser(getUser(commentDto.getUsername()));
    }

    private Project getProject(final Integer projectId) {
        return projectRepo.findById(projectId).orElseThrow(() -> new EntityNotFoundException(
                "Project with id " + projectId + "was not found"));
    }

    private User getUser(final String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(
                "User with name " + username + "was not found"));
    }
}
