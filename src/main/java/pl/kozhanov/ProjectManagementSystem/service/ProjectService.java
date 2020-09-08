package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.Comment;
import pl.kozhanov.ProjectManagementSystem.domain.Project;
import pl.kozhanov.ProjectManagementSystem.domain.UserProjectRoleLink;
import pl.kozhanov.ProjectManagementSystem.repos.CommentRepo;
import pl.kozhanov.ProjectManagementSystem.repos.ProjectRepo;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    UserService userService;
    @Autowired
    ProjectRoleService projectRoleService;
    @Autowired
    ProjectStatusService projectStatusService;

    @Autowired
    CommentRepo commentRepo;

    public ProjectService(UserService userService, ProjectStatusService projectStatusService, ProjectRoleService projectRoleService, ProjectRepo projectRepo) {
        this.userService = userService;
        this.projectStatusService= projectStatusService;
        this.projectRoleService = projectRoleService;
        this.projectRepo = projectRepo;
    }

    public List<Project> findAll() {
        return projectRepo.findAll();
    }

    public ProjectViewProjection findById(Integer id) {
        return projectRepo.findById(id);
    }

    public boolean addProject(String title, String description, String projectManager, List<String> roles, List<String> existingUsers) {
        Project newProject = new Project(Instant.now(), title, description, userService.getCurrentLoggedInUsername(), projectManager, projectStatusService.findByStatusName("Waiting"));
        if (roles != null) {
            Set<UserProjectRoleLink> uprlSet = new HashSet<>();
            for (int i = 0; i < roles.size(); i++) { //lambda or foreach
                uprlSet.add(new UserProjectRoleLink(userService.findByUsername(existingUsers.get(i)), newProject, projectRoleService.findByRoleName(roles.get(i))));
            }
            newProject.setUserProjectRoleLink(uprlSet);
        }
        projectRepo.saveAndFlush(newProject);
        return true;
    }


    public void saveProject(Integer id, String title, String description, String projectManager, List<String> roles, List<String> existingUsers) {
        Project project = projectRepo.getById(id);
        project.setTitle(title);
        project.setDescription(description);
        project.setProjectManager(projectManager);
        project.getUserProjectRoleLink().clear();
        if (roles != null) {
            for (int i = 0; i < roles.size(); i++) {
                project.getUserProjectRoleLink().add(new UserProjectRoleLink(userService.findByUsername(existingUsers.get(i)), project, projectRoleService.findByRoleName(roles.get(i))));
            }
        }
        projectRepo.saveAndFlush(project);
    }

    public void deleteProject(Integer projectId) {
        Project project = projectRepo.getById(projectId);
        projectRepo.delete(project);
    }

    public void changeProjectStatus(Integer id, String status) {
        Project project = projectRepo.getById(id);
        project.setStatus(projectStatusService.findByStatusName(status));
        projectRepo.save(project);
    }


    public void addNewComment(Integer id, String commentText) {
        Project project = projectRepo.getById(id);
        String currentLoggedInUser = userService.getCurrentLoggedInUsername();
        project.getComments().add(new Comment(Instant.now(), commentText, project, userService.findByUsername(currentLoggedInUser)));
        projectRepo.saveAndFlush(project);
    }


    public Page<ProjectViewProjection> findProjects(String projectManagerFilter, String createdByFilter, Pageable pageable) {
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
        //if filter by ProjectManager & Creator selected | if(!projectManagerFilter.equals("") && !createdByFilter.equals(""))
        else {
            return creatorAndPmFilterSelectedReturn(projectManagerFilter, createdByFilter, pageable);
        }

    }

    private Page<ProjectViewProjection> creatorAndPmFilterSelectedReturn(String projectManagerFilter, String createdByFilter, Pageable pageable) {
        if (userService.isAdmin()) {
            return projectRepo.findAllByProjectManagerAndCreator(projectManagerFilter, createdByFilter, pageable);
        } else {
            return projectRepo.findAllWhereUserIsMemberByProjectManagerAndByCreator(userService.findByUsername(userService.getCurrentLoggedInUsername()).getId(), userService.getCurrentLoggedInUsername(), projectManagerFilter, createdByFilter, pageable);
        }
    }

    private Page<ProjectViewProjection> creatorFilterSelectedReturn(String createdByFilter, Pageable pageable) {
        if (userService.isAdmin()) {
            return projectRepo.findAllByCreator(createdByFilter, pageable);
        } else {
            return projectRepo.findAllWhereUserIsMemberByCreator(userService.findByUsername(userService.getCurrentLoggedInUsername()).getId(), userService.getCurrentLoggedInUsername(), createdByFilter, pageable);
        }
    }

    private Page<ProjectViewProjection> projectManagerFilterRerurn(String projectManagerFilter, Pageable pageable) {
        if (userService.isAdmin()) {
            return projectRepo.findAllByProjectManager(projectManagerFilter, pageable);
        } else {
            return projectRepo.findAllWhereUserIsMemberByProjectManager(userService.findByUsername(userService.getCurrentLoggedInUsername()).getId(), userService.getCurrentLoggedInUsername(), projectManagerFilter, pageable);
        }
    }

    private Page<ProjectViewProjection> noFiltersSelectedReturn(Pageable pageable) {
        if (userService.isAdmin()) {
            return projectRepo.getAll(pageable);
        } else {
            return projectRepo.findAllWhereUserIsMember(userService.findByUsername(userService.getCurrentLoggedInUsername()).getId(), userService.getCurrentLoggedInUsername(), pageable);
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

