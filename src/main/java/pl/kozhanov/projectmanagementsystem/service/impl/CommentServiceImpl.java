package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kozhanov.projectmanagementsystem.domain.Comment;
import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.dto.CommentDto;
import pl.kozhanov.projectmanagementsystem.repos.CommentRepo;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;
import pl.kozhanov.projectmanagementsystem.service.CommentService;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentServiceImpl implements CommentService {

    private final ProjectRepo projectRepo;
    private final OrikaBeanMapper mapper;
    private final CommentRepo commentRepo;

    public CommentServiceImpl(final ProjectRepo projectRepo,
                              final OrikaBeanMapper mapper,
                              final CommentRepo commentRepo) {
        this.projectRepo = projectRepo;
        this.mapper = mapper;
        this.commentRepo = commentRepo;
    }

    @Override
    @Transactional
    public Integer addNewComment(final CommentDto commentForAdding) {
        final Project project = projectRepo.findById(commentForAdding.getProjectId()).orElseThrow(() -> new EntityNotFoundException(
                "Project with id " + commentForAdding.getProjectId() + "was not found"));
        final Comment newComment = mapper.map(commentForAdding, Comment.class);
        project.getComments().add(newComment);

        return project.getId();
    }

    @Override
    @Transactional
    public void deleteComment(final Long commentId) {
        final Comment commentForRemoval = commentRepo.findById(commentId).orElseThrow(() -> new EntityNotFoundException(
                "Comment with id " + commentId + "was not found"));

        commentRepo.delete(commentForRemoval);
    }
}
