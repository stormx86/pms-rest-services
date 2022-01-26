package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kozhanov.projectmanagementsystem.domain.Comment;
import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.domain.UserProjectRoleLink;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;
import pl.kozhanov.projectmanagementsystem.service.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final UserService userService;
    private final ProjectRoleService projectRoleService;
    private final ProjectStatusService projectStatusService;
    private final ProjectRepo projectRepo;

    public ProjectServiceImpl(final UserService userService,
                              final ProjectStatusService projectStatusService,
                              final ProjectRoleService projectRoleService,
                              final ProjectRepo projectRepo) {
        this.userService = userService;
        this.projectStatusService = projectStatusService;
        this.projectRoleService = projectRoleService;
        this.projectRepo = projectRepo;
    }

    @Override
    @Transactional
    public List<Project> findAll() {
        return projectRepo.findAll();
    }

    @Override
    @Transactional
    public ProjectViewProjection findById(final Integer id) {
        return projectRepo.findById(id);
    }

    @Override
    @Transactional
    public boolean addProject(final String title,
                              final String description,
                              final String projectManager,
                              final List<String> roles,
                              final List<String> existingUsers) {
        final Project newProject = new Project(
                Instant.now(),
                title,
                description,
                userService.getCurrentLoggedInUsername(),
                projectManager,
                projectStatusService.findByStatusName("Waiting"));
        if (roles != null) {
            Set<UserProjectRoleLink> uprlSet = new HashSet<>();
            IntStream.range(0, roles.size()).forEach(i -> uprlSet.add(new UserProjectRoleLink(
                    userService.findByUsername(existingUsers.get(i)), newProject,
                    projectRoleService.findByRoleName(roles.get(i)))));
            newProject.setUserProjectRoleLink(uprlSet);
        }
        projectRepo.saveAndFlush(newProject);
        return true;
    }

    @Override
    @Transactional
    public void saveProject(final Integer id,
                            final String title,
                            final String description,
                            final String projectManager,
                            final List<String> roles,
                            final List<String> existingUsers) {
        final Project project = projectRepo.getById(id);
        project.setTitle(title);
        project.setDescription(description);
        project.setProjectManager(projectManager);
        project.getUserProjectRoleLink().clear();
        if (roles != null) {
            IntStream.range(0, roles.size()).forEach(i -> project.getUserProjectRoleLink().add(
                    new UserProjectRoleLink(
                            userService.findByUsername(existingUsers.get(i)), project,
                            projectRoleService.findByRoleName(roles.get(i)))));
        }
        projectRepo.saveAndFlush(project);
    }

    @Override
    @Transactional
    public void deleteProject(final Integer projectId) {
        final Project project = projectRepo.getById(projectId);
        projectRepo.delete(project);
    }

    @Override
    @Transactional
    public void changeProjectStatus(final Integer id, final String status) {
        final Project project = projectRepo.getById(id);
        project.setStatus(projectStatusService.findByStatusName(status));
        projectRepo.save(project);
    }

    @Override
    @Transactional
    public void addNewComment(final Integer id, final String commentText) {
        final Project project = projectRepo.getById(id);
        final String currentLoggedInUser = userService.getCurrentLoggedInUsername();
        project.getComments().add(new Comment(Instant.now(), commentText, project,
                userService.findByUsername(currentLoggedInUser)));
        projectRepo.saveAndFlush(project);
    }

    @Override
    @Transactional
    public Page<ProjectViewProjection> findProjects(final String projectManagerFilter,
                                                    final String createdByFilter,
                                                    final Pageable pageable) {
        //if no one filter selected

        if (projectManagerFilter.equals("") && createdByFilter.equals("")) {
            return noFiltersSelectedReturn(pageable);
        }
        //if filter by ProjectManager selected
        else if (!projectManagerFilter.equals("") && createdByFilter.equals("")) {
            return projectManagerFilterRerurn(projectManagerFilter, pageable);
        }
        //if filter by Creator selected
        else if (projectManagerFilter.equals("") && !createdByFilter.equals("")) {
            return creatorFilterSelectedReturn(createdByFilter, pageable);
        }
        //if filter by ProjectManager & Creator selected
        else {
            return creatorAndPmFilterSelectedReturn(projectManagerFilter, createdByFilter, pageable);
        }
    }

    private Page<ProjectViewProjection> creatorAndPmFilterSelectedReturn(final String projectManagerFilter,
                                                                         final String createdByFilter,
                                                                         final Pageable pageable) {
        if (userService.isAdmin()) {
            return projectRepo.findAllByProjectManagerAndCreator(projectManagerFilter, createdByFilter, pageable);
        } else {
            return projectRepo.findAllWhereUserIsMemberByProjectManagerAndByCreator(userService.findByUsername(
                    userService.getCurrentLoggedInUsername()).getId(),
                    userService.getCurrentLoggedInUsername(), projectManagerFilter, createdByFilter, pageable);
        }
    }

    private Page<ProjectViewProjection> creatorFilterSelectedReturn(final String createdByFilter, final Pageable pageable) {
        if (userService.isAdmin()) {
            return projectRepo.findAllByCreator(createdByFilter, pageable);
        } else {
            return projectRepo.findAllWhereUserIsMemberByCreator(userService.findByUsername(
                    userService.getCurrentLoggedInUsername()).getId(),
                    userService.getCurrentLoggedInUsername(), createdByFilter, pageable);
        }
    }

    private Page<ProjectViewProjection> projectManagerFilterRerurn(final String projectManagerFilter, final Pageable pageable) {
        if (userService.isAdmin()) {
            return projectRepo.findAllByProjectManager(projectManagerFilter, pageable);
        } else {
            return projectRepo.findAllWhereUserIsMemberByProjectManager(userService.findByUsername(
                    userService.getCurrentLoggedInUsername()).getId(),
                    userService.getCurrentLoggedInUsername(), projectManagerFilter, pageable);
        }
    }

    private Page<ProjectViewProjection> noFiltersSelectedReturn(final Pageable pageable) {
        if (userService.isAdmin()) {
            return projectRepo.getAll(pageable);
        } else {
            return projectRepo.findAllWhereUserIsMember(userService.findByUsername(
                    userService.getCurrentLoggedInUsername()).getId(),
                    userService.getCurrentLoggedInUsername(), pageable);
        }
    }

    public Sort sortManage(Sort sort) {
        if (sort.isSorted()) {
            if (sort.iterator().next().getDirection().isAscending()) {
                sort = sort.descending();
            } else {
                sort = sort.ascending();
            }
        }
        return sort;
    }
}

