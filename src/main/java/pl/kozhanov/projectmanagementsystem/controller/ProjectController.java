package pl.kozhanov.projectmanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.projectmanagementsystem.dto.ProjectDto;
import pl.kozhanov.projectmanagementsystem.dto.ProjectRoleDto;
import pl.kozhanov.projectmanagementsystem.service.ProjectRoleService;
import pl.kozhanov.projectmanagementsystem.service.ProjectService;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;
    private final ProjectRoleService projectRoleService;

    public ProjectController(final ProjectService projectService,
                             final ProjectRoleService projectRoleService) {
        this.projectService = projectService;
        this.projectRoleService = projectRoleService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProjectDto>> getProjects(@RequestHeader(value = "userId") final Integer userId) {

        final List<ProjectDto> projectList = projectService.findProjects(userId);
        LOGGER.info("Retrieving the whole project list.");
        return isEmpty(projectList) ? ResponseEntity.noContent().build() : ResponseEntity.ok(projectList);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/project", produces = "application/json")
    public ResponseEntity<ProjectDto> getProjectDetails(@RequestHeader(value = "userId") final Integer userId,
                                                        @RequestParam(value = "projectId") final Integer projectId) throws AccessDeniedException {

        final ProjectDto project = projectService.findProjectById(projectId, userId);
        LOGGER.info("Retrieving project details for project id: " + projectId);
        return ResponseEntity.ok(project);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/roles", produces = "application/json")
    public ResponseEntity<List<ProjectRoleDto>> getAllProjectRoleNames() {

        final List<ProjectRoleDto> projectRoles = projectRoleService.findAllRoleNames();
        LOGGER.info("Retrieving all existing project role names.");
        return isEmpty(projectRoles) ? ResponseEntity.noContent().build() : ResponseEntity.ok(projectRoles);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/project", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createProject(@RequestBody @Valid final ProjectDto newProjectDto) {

        final Integer newProjectId = projectService.createProject(newProjectDto);
        LOGGER.info("Creating new project.");
        return newProjectId != null ? ResponseEntity.ok(newProjectId) : ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/project", produces = "application/json")
    public ResponseEntity<Void> deleteProject(@RequestParam(value = "projectId") final Integer projectId) {

        projectService.deleteProject(projectId);
        LOGGER.info("Deleting project with id: " + projectId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping(value = "/project/edit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> updateProject(@RequestParam(value = "projectId") final Integer projectId,
                                                 @RequestBody @Valid final ProjectDto updatedProjectDto) {
        final Integer updatedProjectId = projectService.updateProject(projectId, updatedProjectDto);
        LOGGER.info("Updating project with id: " + updatedProjectId);
        return updatedProjectId != null ? ResponseEntity.ok(updatedProjectId) : ResponseEntity.noContent().build();
    }
}