package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.ProjectRole;
import pl.kozhanov.ProjectManagementSystem.domain.User;
import pl.kozhanov.ProjectManagementSystem.repos.ProjectRoleRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectRoleService {

    @Autowired
    ProjectRoleRepo projectRoleRepo;

    public ProjectRole findByRoleName(String rolename){ return projectRoleRepo.findByRoleName(rolename); }

    public List<ProjectRole> findAllByOrderByRoleName(){return projectRoleRepo.findAllByOrderByRoleName();}

    public List<String> findAllRoleNames(){
        List<String> existingRoles = new ArrayList<>();
        for(ProjectRole pr:projectRoleRepo.findAll()){
            existingRoles.add(pr.getRoleName());
        }
        return existingRoles;
    };

    public void addNewProjectRole(ProjectRole projectRole){
        projectRoleRepo.save(projectRole);
    }

    public void deleteProjectRole(ProjectRole projectRole){
        projectRoleRepo.delete(projectRole);
    }
}
