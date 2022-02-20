package pl.kozhanov.projectmanagementsystem.service;

import pl.kozhanov.projectmanagementsystem.dto.CommentDto;

public interface CommentService {

    Integer addNewComment(CommentDto newComment);

    void deleteComment(Long commentId);

}
