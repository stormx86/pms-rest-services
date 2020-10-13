package pl.kozhanov.projectmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.projectmanagementsystem.domain.Comment;
import pl.kozhanov.projectmanagementsystem.repos.CommentRepo;

@Service
public class CommentService {
    private CommentRepo commentRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void delComment(Long commentId) {
        Comment cmt = commentRepo.getById(commentId);
        commentRepo.delete(cmt);
    }
}
