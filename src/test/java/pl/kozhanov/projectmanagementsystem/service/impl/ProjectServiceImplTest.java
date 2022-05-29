package pl.kozhanov.projectmanagementsystem.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.domain.UserProjectRoleLink;
import pl.kozhanov.projectmanagementsystem.dto.ProjectDto;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;
import pl.kozhanov.projectmanagementsystem.repos.UserRepo;
import pl.kozhanov.projectmanagementsystem.service.UserService;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import java.nio.file.AccessDeniedException;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;

public class ProjectServiceImplTest {

    private static final Integer PROJECT_ID = 100;
    private static final Integer USER_ID = 20;
    private static final String USERNAME = "Name";

    @Mock
    private ProjectRepo projectRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private UserService userService;
    @Mock
    private OrikaBeanMapper mapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeClass
    public void beforeClass() {
        openMocks(this);
    }

    @BeforeMethod
    public void resetMocks() {
        reset(projectRepo, userRepo, userService, mapper);
    }

    @Test
    public void shouldFindAllProjects() {
        //given
        final Project project = new Project();
        project.setId(PROJECT_ID);
        when(projectRepo.findAll()).thenReturn(Collections.singletonList(project));

        //when
        final List<Project> projectList = projectService.findAll();

        //then
        assertEquals(projectList.get(0).getId(), PROJECT_ID);
        verify(projectRepo, times(1)).findAll();
    }

    @Test
    public void shouldFindProjectById() throws AccessDeniedException {
        //given
        final User user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        final UserProjectRoleLink userProjectRoleLink = new UserProjectRoleLink();
        userProjectRoleLink.setUser(user);
        final Project project = new Project();
        project.setId(PROJECT_ID);
        project.setUserProjectRoleLink(Collections.singleton(userProjectRoleLink));
        final ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(PROJECT_ID);
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        when(projectRepo.findById(anyInt())).thenReturn(Optional.of(project));
        when(mapper.map(any(Project.class), eq(ProjectDto.class))).thenReturn(projectDto);

        //when
        projectService.findProjectById(PROJECT_ID, USER_ID);

        //then
        verify(userRepo, times(1)).findById(anyInt());
        verify(projectRepo, times(1)).findById(anyInt());
        verify(mapper, times(1)).map(any(Project.class), eq(ProjectDto.class));
    }

    @Test
    public void shouldFindProjectsWhenUserIsAdmin() {
        //given
        final User user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        final Project project = new Project();
        project.setId(PROJECT_ID);
        final ProjectDto projectDto = new ProjectDto();
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        when(userService.isAdmin(any())).thenReturn(true);
        when(projectRepo.findAll()).thenReturn(Collections.singletonList(project));
        when(mapper.map(any(Project.class), eq(ProjectDto.class))).thenReturn(projectDto);

        //when
        projectService.findProjects(USER_ID);

        //then
        verify(userRepo, times(1)).findById(anyInt());
        verify(userService, times(1)).isAdmin(any());
        verify(projectRepo, times(1)).findAll();
        verify(mapper, times(1)).map(any(Project.class), eq(ProjectDto.class));
    }

    @Test
    public void shouldGetProjectsForAdmin() {
        //given
        final Project project = new Project();
        project.setId(PROJECT_ID);
        final ProjectDto projectDto = new ProjectDto();
        when(projectRepo.findAll()).thenReturn(Collections.singletonList(project));
        when(mapper.map(any(Project.class), eq(ProjectDto.class))).thenReturn(projectDto);

        //when
        projectService.getProjectsForAdmin();

        //then
        verify(projectRepo, times(1)).findAll();
        verify(mapper, times(1)).map(any(Project.class), eq(ProjectDto.class));
    }

    @Test
    public void shouldGetProjectsForMember() {
        //given
        final User user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        final UserProjectRoleLink userProjectRoleLink = new UserProjectRoleLink();
        userProjectRoleLink.setUser(user);
        final Project project = new Project();
        project.setId(PROJECT_ID);
        project.setUserProjectRoleLink(Collections.singleton(userProjectRoleLink));
        final ProjectDto projectDto = new ProjectDto();
        when(projectRepo.findAll()).thenReturn(Collections.singletonList(project));
        when(mapper.map(any(Project.class), eq(ProjectDto.class))).thenReturn(projectDto);

        //when
        projectService.getProjectsForMember(user);

        //then
        verify(projectRepo, times(1)).findAll();
        verify(mapper, times(1)).map(any(Project.class), eq(ProjectDto.class));
    }

    @Test
    public void shouldCreateProject() {
        //given
        final Project project = new Project();
        project.setId(PROJECT_ID);
        final ProjectDto projectDto = new ProjectDto();
        when(mapper.map(any(ProjectDto.class), eq(Project.class))).thenReturn(project);

        //when
        projectService.createProject(projectDto);

        //then
        verify(projectRepo, times(1)).save(any());
        verify(mapper, times(1)).map(any(ProjectDto.class), eq(Project.class));
    }

    @Test
    public void shouldUpdateProject() {
        //given
        final User user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        final UserProjectRoleLink userProjectRoleLink = new UserProjectRoleLink();
        userProjectRoleLink.setUser(user);
        final Set<UserProjectRoleLink> userProjectRoleLinkList = new HashSet<>();
        userProjectRoleLinkList.add(userProjectRoleLink);
        final Project project = new Project();
        project.setId(PROJECT_ID);
        project.setUserProjectRoleLink(userProjectRoleLinkList);
        final ProjectDto projectDto = new ProjectDto();
        when(projectRepo.findById(anyInt())).thenReturn(Optional.of(project));
        when(mapper.map(any(ProjectDto.class), eq(Project.class))).thenReturn(project);

        //when
        projectService.updateProject(PROJECT_ID, projectDto);

        //then
        verify(projectRepo, times(1)).findById(anyInt());
        verify(projectRepo, times(1)).save(any());
        verify(mapper, times(1)).map(any(ProjectDto.class), eq(Project.class));
    }

    @Test
    public void shouldDeleteProject() {
        //given
        final Project project = new Project();
        project.setId(PROJECT_ID);
        when(projectRepo.findById(anyInt())).thenReturn(Optional.of(project));

        //when
        projectService.deleteProject(PROJECT_ID);

        //then
        verify(projectRepo, times(1)).delete(any());
        verify(projectRepo, times(1)).findById(anyInt());
    }
}