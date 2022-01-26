package pl.kozhanov.projectmanagementsystem.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.kozhanov.projectmanagementsystem.domain.Comment;
import pl.kozhanov.projectmanagementsystem.repos.CommentRepo;
import pl.kozhanov.projectmanagementsystem.service.impl.CommentServiceImpl;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {
    private CommentRepo commentRepo = mock(CommentRepo.class);
    private CommentServiceImpl commentService = new CommentServiceImpl(commentRepo);

    @Test
    void delCommentTest() {
        long id = 1;
        Comment comment = new Comment();
        when(commentRepo.getById(anyLong())).thenReturn(comment);
        commentService.deleteComment(id);
        verify(commentRepo, Mockito.times(1)).delete(comment);
    }
}