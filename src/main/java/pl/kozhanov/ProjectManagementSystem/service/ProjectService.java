package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.Project;
import pl.kozhanov.ProjectManagementSystem.domain.User;
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

    public List<Project> findAll(){
        return  projectRepo.findAll();
    }

    public ProjectView findById(Integer id){return projectRepo.findById(id);}

    public List<ProjectView> findAllByOrderByCreatedAtDesc(){return projectRepo.findAllByOrderByCreatedAtDesc();}

    public void addProject(String title, String description, String pmUser){
        Project newProject = new Project(LocalDateTime.now(), title, description, "Waiting");
        Set<UserProjectRoleLink> uprlSet = new HashSet<>();
        //after authorization implementation change Anton to currently authorized uzer
        uprlSet.add(new UserProjectRoleLink(userService.findByUsername("Anton"), newProject, projectRoleService.findByRoleName("Creator")));
        //as option could be implemented realization with Map<User, Role> to add roles dynamically in loop depending on received Map parameter
        uprlSet.add(new UserProjectRoleLink(userService.findByUsername(pmUser), newProject, projectRoleService.findByRoleName("ProjectManager")));
        newProject.setUserProjectRoleLink(uprlSet);
        projectRepo.save(newProject);
    }

    public void saveProject(Integer id, String title, String description, String pmUser){

        Project project = projectRepo.getById(id);
        project.setTitle(title);
        project.setDescription(description);
        // in case adding more roles to the project there is an option to get here Map<role, user(name)> and save one by one
        project.getUserProjectRoleLink().stream().forEach(
                a ->{if (a.getProjectRoles().getRoleName().equals("ProjectManager")) {a.setUser(userService.findByUsername(pmUser));}});
        projectRepo.save(project);
    }



}

