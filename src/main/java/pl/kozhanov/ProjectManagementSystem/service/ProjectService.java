package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.Comment;
import pl.kozhanov.ProjectManagementSystem.domain.Project;
import pl.kozhanov.ProjectManagementSystem.domain.UserProjectRoleLink;
import pl.kozhanov.ProjectManagementSystem.repos.CommentRepo;
import pl.kozhanov.ProjectManagementSystem.repos.ProjectRepo;

import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
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


    public List<Project> findAll(){
        return  projectRepo.findAll();
    }

    public ProjectViewProjection findById(Integer id){return projectRepo.findById(id);}


    public void addProject(String title, String description, String projectManager, String[] roles, String[] users){
        Project newProject = new Project(Instant.now(), title, description, userService.getCurrentLoggedInUsername(), projectManager, projectStatusService.findByStatusName("Waiting"));
        if(!roles[0].equals("none")) {
            Set<UserProjectRoleLink> uprlSet = new HashSet<>();
            for (int i = 0; i < roles.length; i++) {
                uprlSet.add(new UserProjectRoleLink(userService.findByUsername(users[i]), newProject, projectRoleService.findByRoleName(roles[i])));
            }
            //after authorization implementation change to findByUsername(currently authorized user) или как-то иначе добавить отдельно
            //роль Creator к проекту. Если в списке при создании уже не будет роли Creator к выбору
            newProject.setUserProjectRoleLink(uprlSet);
        }
        projectRepo.saveAndFlush(newProject);
    }

    public void deleteProject(Integer projectId){
        Project project = projectRepo.getById(projectId);
        projectRepo.delete(project);
    }

    public void saveProject(Integer id, String title, String description, String projectManager, String[] roles, String[] users){
        Project project = projectRepo.getById(id);
        project.setTitle(title);
        project.setDescription(description);
        project.setProjectManager(projectManager);
        project.getUserProjectRoleLink().clear();
        if(!roles[0].equals("none")) {
            for (int i = 0; i < roles.length; i++) {
                project.getUserProjectRoleLink().add(new UserProjectRoleLink(userService.findByUsername(users[i]), project, projectRoleService.findByRoleName(roles[i])));
            }
        }
        projectRepo.saveAndFlush(project);
    }

    public void changeProjectStatus(Integer id, String status){
        Project project = projectRepo.getById(id);
        project.setStatus(projectStatusService.findByStatusName(status));
        projectRepo.save(project);
    }


    public void addNewComment(Integer id, String commentText){
        Project project = projectRepo.getById(id);
        String currentLoggedInUser = userService.getCurrentLoggedInUsername();
        project.getComments().add(new Comment(Instant.now(), commentText, project, userService.findByUsername(currentLoggedInUser)));
        projectRepo.saveAndFlush(project);
    }

    public List<ProjectViewProjection> checkIfCurrentLoggedInUserIsMember(List<ProjectViewProjection> projectList)
    {
        String currentLoggedInUser = userService.getCurrentLoggedInUsername();
        for(Iterator<ProjectViewProjection> it = projectList.iterator(); it.hasNext();)
        {
            ProjectViewProjection p = it.next();
            if(!userService.findAllUsersOnProject(p.getId()).contains(currentLoggedInUser) &&
            !userService.isCreator(currentLoggedInUser, p.getId()) &&
            !userService.isProjectManager(currentLoggedInUser, p.getId()) &&
            !userService.isAdmin())
            {
                it.remove();
            }
        }
        return projectList;
    }

    public List<ProjectViewProjection> findProjects(String projectManagerFilter, String createdByFilter){
        //if no one filter selected
        if(projectManagerFilter.equals("") && createdByFilter.equals(""))
        {
            return checkIfCurrentLoggedInUserIsMember(projectRepo.findAllByOrderByCreatedAtDesc());
        }
        //if filter by ProjectManager selected
        else if (!projectManagerFilter.equals("") && createdByFilter.equals(""))
        {
            return checkIfCurrentLoggedInUserIsMember(projectRepo.findAllByProjectManagerOrderByCreatedAtDesc(projectManagerFilter));
        }
        //if filter by Creator selected
        else if(projectManagerFilter.equals("") && !createdByFilter.equals(""))
        {
            return checkIfCurrentLoggedInUserIsMember(projectRepo.findAllByCreatorOrderByCreatedAtDesc(createdByFilter));
        }
        //if filter by ProjectManager & Creator selected | if(!projectManagerFilter.equals("") && !createdByFilter.equals(""))
        else
        {
            return checkIfCurrentLoggedInUserIsMember(projectRepo.findAllByProjectManagerAndCreatorOrderByCreatedAtDesc(projectManagerFilter, createdByFilter));
        }

    }


}

