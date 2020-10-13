package pl.kozhanov.projectmanagementsystem.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.kozhanov.projectmanagementsystem.domain.Comment;
import pl.kozhanov.projectmanagementsystem.repos.CommentRepo;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

class CommentServiceTest {
    private CommentRepo commentRepo = mock(CommentRepo.class);
    private CommentService commentService = new CommentService(commentRepo);

    @Test
    void delCommentTest() {
        long id = 1;
        Comment comment = new Comment();
        Mockito.when(commentRepo.getById(anyLong())).thenReturn(comment);
        commentService.delComment(id);
        Mockito.verify(commentRepo, Mockito.times(1)).delete(comment);
    }
}