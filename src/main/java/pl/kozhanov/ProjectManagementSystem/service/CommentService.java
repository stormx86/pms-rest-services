package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.Comment;
import pl.kozhanov.ProjectManagementSystem.repos.CommentRepo;

@Service
public class CommentService {

    @Autowired
    CommentRepo commentRepo;

    public void delComment(Integer commentId) {
        Comment cmt = commentRepo.getById(commentId);
        commentRepo.delete(cmt);
    }
}
