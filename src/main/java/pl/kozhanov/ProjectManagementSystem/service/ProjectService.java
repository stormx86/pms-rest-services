package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.Comment;
import pl.kozhanov.ProjectManagementSystem.domain.Project;
import pl.kozhanov.ProjectManagementSystem.domain.UserProjectRoleLink;
import pl.kozhanov.ProjectManagementSystem.repos.ProjectRepo;

import java.time.LocalDateTime;
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

    public List<Project> findAll(){
        return  projectRepo.findAll();
    }

    public ProjectView findById(Integer id){return projectRepo.findById(id);}

    public List<ProjectView> findAllByOrderByCreatedAtDesc(){return projectRepo.findAllByOrderByCreatedAtDesc();}

    public void addProject(String title, String description, String[] roles, String[] users){
        Project newProject = new Project(LocalDateTime.now(), title, description, projectStatusService.findByStatusName("Waiting"));
        Set<UserProjectRoleLink> uprlSet = new HashSet<>();
        for(int i=0; i<roles.length; i++)
        {
            uprlSet.add(new UserProjectRoleLink(userService.findByUsername(users[i]), newProject, projectRoleService.findByRoleName(roles[i])));
        }
        //after authorization implementation change to findByUsername(currently authorized user) или как-то иначе добавить отдельно
        //роль Creator к проекту. Если в списке при создании уже не будет роли Creator к выбору
        newProject.setUserProjectRoleLink(uprlSet);
        projectRepo.saveAndFlush(newProject);
    }

    public void saveProject(Integer id, String title, String description, String[] roles, String[] users){
        Project project = projectRepo.getById(id);
        project.setTitle(title);
        project.setDescription(description);
        project.getUserProjectRoleLink().clear();

        for(int i=0; i<roles.length; i++)
            {
                project.getUserProjectRoleLink().add(new UserProjectRoleLink(userService.findByUsername(users[i]), project, projectRoleService.findByRoleName(roles[i])));
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
        //after authorization implementation change to findByUsername(currently authorized user) или как-то иначе добавить отдельно
        project.getComments().add(new Comment(LocalDateTime.now(), commentText, project, userService.findByUsername("Anton")));
        projectRepo.saveAndFlush(project);
    }

}

