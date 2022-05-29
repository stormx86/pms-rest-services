package pl.kozhanov.projectmanagementsystem.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.domain.ProjectStatus;
import pl.kozhanov.projectmanagementsystem.dto.NewProjectStatusRequestDto;
import pl.kozhanov.projectmanagementsystem.dto.ProjectStatusDto;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

public class ProjectStatusServiceImplTest {

    @Mock
    private ProjectRepo projectRepo;

    @Mock
    private OrikaBeanMapper mapper;

    @InjectMocks
    private ProjectStatusServiceImpl projectStatusService;

    @BeforeClass
    public void beforeClass() {
        openMocks(this);
    }

    @BeforeMethod
    public void resetMocks() {
        reset(projectRepo, mapper);
    }

    @Test
    public void findFindAllStatuses() {
        //given
        final ProjectStatusDto projectStatusDto = mock(ProjectStatusDto.class);
        when(mapper.map(any(), any())).thenReturn(projectStatusDto);

        //when
        final List<ProjectStatusDto> result = projectStatusService.findAllStatuses();

        //then
        assertEquals(result.size(), 3);
        verify(mapper, times(3)).map(any(), any());
    }

    @Test
    public void shouldSetNewProjectStatus() {
        //given
        final NewProjectStatusRequestDto requestDto = new NewProjectStatusRequestDto();
        requestDto.setProjectId(10);
        requestDto.setNewStatus(ProjectStatus.PROCESSING.name());
        final Project project = new Project();
        project.setId(10);
        project.setStatus(ProjectStatus.WAITING);
        when(projectRepo.findById(anyLong())).thenReturn(Optional.of(project));

        //when
        projectStatusService.setNewProjectStatus(requestDto);

        //then
        verify(eq(project), times(1)).setStatus(any());
    }
}