package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void addProject(String title, String description, String pmUser){
        Project newProject = new Project(LocalDateTime.now(), title, description, projectStatusService.findByStatusName("Waiting"));
        Set<UserProjectRoleLink> uprlSet = new HashSet<>();
        //after authorization implementation change Anton to currently authorized user
        uprlSet.add(new UserProjectRoleLink(userService.findByUsername("Anton"), newProject, projectRoleService.findByRoleName("Creator")));
        //as option could be implemented realization with Map<User, Role> to add roles dynamically in loop depending on received Map parameter
        uprlSet.add(new UserProjectRoleLink(userService.findByUsername(pmUser), newProject, projectRoleService.findByRoleName("ProjectManager")));
        newProject.setUserProjectRoleLink(uprlSet);
        projectRepo.save(newProject);
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

}

