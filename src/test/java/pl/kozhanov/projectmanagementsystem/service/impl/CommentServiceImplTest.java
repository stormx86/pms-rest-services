package pl.kozhanov.projectmanagementsystem.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.kozhanov.projectmanagementsystem.domain.Comment;
import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.dto.CommentDto;
import pl.kozhanov.projectmanagementsystem.repos.CommentRepo;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

public class CommentServiceImplTest {

    private static final Integer PROJECT_ID = 100;
    private static final Long COMMENT_ID = 50L;

    @Mock
    private ProjectRepo projectRepo;

    @Mock
    private OrikaBeanMapper mapper;

    @Mock
    private CommentRepo commentRepo;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeClass
    public void beforeClass(){openMocks(this);}

    @BeforeMethod
    public void resetMocks(){
        reset(projectRepo, mapper, commentRepo);
    }

    @Test
    public void shouldAddNewComment() {
        //given
        final Project project = new Project();
        project.setId(100);
        project.setComments(new ArrayList<>());
        final CommentDto commentDto = new CommentDto();
        commentDto.setProjectId(100);
        final Comment comment = mock(Comment.class);
        when(projectRepo.findById(project.getId())).thenReturn(Optional.of(project));
        when(mapper.map(commentDto, Comment.class)).thenReturn(comment);

        //when
        final Integer result = commentService.addNewComment(commentDto);

        //then
        assertEquals(result, PROJECT_ID);
        assertEquals(project.getComments().get(0), comment);
    }

    @Test
    public void shouldDeleteComment() {
        //given
        final Comment comment = mock(Comment.class);
        when(commentRepo.findById(anyLong())).thenReturn(Optional.of(comment));

        //when
        commentService.deleteComment(COMMENT_ID);

        //then
        verify(commentRepo, times(1)).delete(comment);
    }
}