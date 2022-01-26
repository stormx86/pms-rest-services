package pl.kozhanov.projectmanagementsystem.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.domain.ProjectStatus;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;
import pl.kozhanov.projectmanagementsystem.service.impl.ProjectServiceImpl;
import pl.kozhanov.projectmanagementsystem.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceImplTest {
    private UserServiceImpl userService = mock(UserServiceImpl.class);
    private ProjectStatusService projectStatusService = mock(ProjectStatusService.class);
    private ProjectRoleService projectRoleService = mock(ProjectRoleService.class);
    private ProjectRepo projectRepo = mock(ProjectRepo.class);

    private ProjectServiceImpl projectService = new ProjectServiceImpl(userService, projectStatusService, projectRoleService, projectRepo);

    @Test
    void addProjectTest() {
        String title = "Title";
        String description = "Description";
        String projectManager = "PM";
        List<String> roles = new ArrayList<>();
        roles.add("Creator");
        List<String> existingUsers = new ArrayList<>();
        existingUsers.add("Nick");
        assertTrue(projectService.addProject(title, description, projectManager, roles, existingUsers));
    }

    @Test
    void saveProjectTest() {
        Integer id = 1;
        Project project = new Project();
        project.setUserProjectRoleLink(new HashSet<>());
        String title = "Title";
        String description = "Description";
        String projectManager = "PM";
        List<String> roles = new ArrayList<>();
        roles.add("Creator");
        List<String> existingUsers = new ArrayList<>();
        existingUsers.add("Nick");
        when(projectRepo.getById(id)).thenReturn(project);
        projectService.saveProject(id, title, description, projectManager, roles, existingUsers);
        assertEquals("PM", project.getProjectManager());
    }

    @Test
    void changeProjectStatusTest() {
        Integer id = 1;
        String status = "new status";
        Project project = new Project();
        ProjectStatus ps = new ProjectStatus();
        ps.setStatusName(status);
        when(projectRepo.getById(id)).thenReturn(project);
        when(projectStatusService.findByStatusName(status)).thenReturn(ps);
        projectService.changeProjectStatus(id, status);
        assertEquals("new status", project.getStatus().getStatusName());
    }

    @Test
    void addNewCommentTest() {
        User user = new User();
        Integer id = 1;
        String commentText = "new comment";
        Project project = new Project();
        project.setComments(new ArrayList<>());
        when(projectRepo.getById(id)).thenReturn(project);
        when(userService.getCurrentLoggedInUsername()).thenReturn("Nick");
        when(userService.findByUsername(anyString())).thenReturn(user);
        projectService.addNewComment(id, commentText);
        assertEquals("new comment", project.getComments().get(0).getCommentText());
    }
}