package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.dto.ProjectDto;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;
import pl.kozhanov.projectmanagementsystem.repos.UserRepo;
import pl.kozhanov.projectmanagementsystem.service.ProjectService;
import pl.kozhanov.projectmanagementsystem.service.UserService;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;
    private final UserService userService;
    private final OrikaBeanMapper mapper;

    public ProjectServiceImpl(final ProjectRepo projectRepo,
                              final UserRepo userRepo,
                              final UserService userService,
                              final OrikaBeanMapper mapper) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public List<Project> findAll() {
        return projectRepo.findAll();
    }

    @Override
    @Transactional
    public ProjectDto findProjectById(final Integer projectId,
                                      final Integer userId) throws AccessDeniedException {
        final User currentUser = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + "was not found"));

        final Project project = projectRepo.findById(projectId).orElseThrow(() -> new EntityNotFoundException(
                "Project with id " + projectId + "was not found"));

        if (!isUserMember(project, currentUser.getUsername())) throw new AccessDeniedException("403 Forbidden");

        return mapper.map(project, ProjectDto.class);
    }

    @Override
    @Transactional
    public List<ProjectDto> findProjects(final Integer userId) {
        final User currentUser = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + "was not found"));

        return userService.isAdmin(currentUser) ? getProjectsForAdmin() : getProjectsForMember(currentUser);
    }

    @Override
    @Transactional
    public List<ProjectDto> getProjectsForAdmin() {
        return projectRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Project::getCreatedAt).reversed())
                .map(project -> mapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ProjectDto> getProjectsForMember(final User user) {
        return projectRepo.findAll()
                .stream()
                .filter(project -> isUserMember(project, user.getUsername()))
                .sorted(Comparator.comparing(Project::getCreatedAt).reversed())
                .map(project -> mapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }

    private boolean isUserMember(final Project project, final String userName) {
        return userName.equals(project.getProjectManager())
                || userName.equals(project.getCreator())
                || hasUserOtherRole(project, userName);
    }

    private boolean hasUserOtherRole(final Project project, final String userName) {
        return project.getUserProjectRoleLink()
                .stream()
                .map(userProjectRoleLink -> userProjectRoleLink.getUser().getUsername())
                .anyMatch(userName::equals);
    }

    @Override
    @Transactional
    public Integer createProject(final ProjectDto newProjectDto) {
        final Project createdProject = mapper.map(newProjectDto, Project.class);
        projectRepo.save(createdProject);

        return createdProject.getId();
    }

    @Override
    @Transactional
    public Integer updateProject(final Integer projectId, final ProjectDto projectForUpdateDto) {
        final Project existingProject = projectRepo.findById(projectId).orElseThrow(() -> new EntityNotFoundException(
                "Project with id " + projectId + "was not found"));
        final Project updatedProject = mapper.map(projectForUpdateDto, Project.class);

        existingProject.setProjectManager(updatedProject.getProjectManager());
        existingProject.setTitle(updatedProject.getTitle());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.getUserProjectRoleLink().clear();
        updatedProject.getUserProjectRoleLink().forEach(rolesCollection -> existingProject.getUserProjectRoleLink().add(rolesCollection));
        projectRepo.save(existingProject);

        return existingProject.getId();
    }

    @Override
    @Transactional
    public void deleteProject(final Integer projectId) {
        final Project project = projectRepo.findById(projectId).orElseThrow(() -> new EntityNotFoundException(
                "Project with id " + projectId + "was not found"));

        projectRepo.delete(project);
    }
}