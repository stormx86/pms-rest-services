package pl.kozhanov.projectmanagementsystem.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;
import pl.kozhanov.projectmanagementsystem.dto.ProjectRoleDto;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRoleRepo;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

public class ProjectRoleServiceImplTest {

    private static final String ROLE_NAME = "RoleName";
    private static final Integer ROLE_ID = 10;

    @Mock
    private ProjectRoleRepo projectRoleRepo;

    @Mock
    private OrikaBeanMapper mapper;

    @InjectMocks
    private ProjectRoleServiceImpl projectRoleService;

    @BeforeClass
    public void beforeClass() {
        openMocks(this);
    }

    @BeforeMethod
    public void resetMocks() {
        reset(projectRoleRepo, mapper);
    }

    @Test
    public void shouldFindAllRoleNames() {
        //given
        final ProjectRole projectRole = mock(ProjectRole.class);
        final ProjectRoleDto projectRoleDto = new ProjectRoleDto();
        projectRoleDto.setRoleName(ROLE_NAME);
        when((projectRoleRepo.findAll())).thenReturn(singletonList(projectRole));
        when(mapper.map(projectRole, ProjectRoleDto.class)).thenReturn(projectRoleDto);

        //when
        final List<ProjectRoleDto> projectRoleDtoList = projectRoleService.findAllRoleNames();

        //then
        assertEquals(projectRoleDtoList.get(0).getRoleName(), ROLE_NAME);
        verify(projectRoleRepo, times(1)).findAll();
        verify(mapper, times(1)).map(any(), any());
    }

    @Test
    public void shouldAddNewProjectRole() {
        //given
        final ProjectRoleDto projectRoleDto = new ProjectRoleDto();
        projectRoleDto.setRoleName(ROLE_NAME);
        final ProjectRole projectRole = new ProjectRole();
        projectRole.setRoleName(ROLE_NAME);
        when(projectRoleRepo.findAll()).thenReturn(singletonList(projectRole));
        when(mapper.map(projectRoleDto, ProjectRole.class)).thenReturn(projectRole);
        when(mapper.map(any(ProjectRole.class), eq(ProjectRoleDto.class))).thenReturn(projectRoleDto);

        //when
        final List<ProjectRoleDto> projectRoleDtoList = projectRoleService.addNewProjectRole(projectRoleDto);

        //then
        assertEquals(projectRoleDtoList.size(), 1);
        assertEquals(projectRoleDtoList.get(0).getRoleName(), ROLE_NAME);
        verify(mapper, times(1)).map(any(ProjectRoleDto.class), eq(ProjectRole.class));
        verify(mapper, times(1)).map(any(ProjectRole.class), eq(ProjectRoleDto.class));
        verify(projectRoleRepo, times(1)).save(any(ProjectRole.class));
    }

    @Test
    public void shouldDeleteProjectRole() {
        //given
        final ProjectRole projectRole = new ProjectRole();
        final ProjectRoleDto projectRoleDto = new ProjectRoleDto();
        when(projectRoleRepo.findById(ROLE_ID)).thenReturn(Optional.of(projectRole));
        when(projectRoleRepo.findAll()).thenReturn(singletonList(projectRole));
        when(mapper.map(any(ProjectRole.class), eq(ProjectRoleDto.class))).thenReturn(projectRoleDto);

        //when
        projectRoleService.deleteProjectRole(ROLE_ID);

        //then
        verify(mapper, times(1)).map(any(ProjectRole.class), eq(ProjectRoleDto.class));
        verify(projectRoleRepo, times(1)).delete(any(ProjectRole.class));
        verify(projectRoleRepo, times(1)).findAll();
        verify(projectRoleRepo, times(1)).findById(anyInt());
    }
}