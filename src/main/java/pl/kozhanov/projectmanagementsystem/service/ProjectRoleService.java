package pl.kozhanov.projectmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRoleRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectRoleService {

    private ProjectRoleRepo projectRoleRepo;

    @Autowired
    public ProjectRoleService(ProjectRoleRepo projectRoleRepo) {
        this.projectRoleRepo = projectRoleRepo;
    }

    ProjectRole findByRoleName(String rolename) {
        return projectRoleRepo.findByRoleName(rolename);
    }

    public List<ProjectRole> findAllByOrderByRoleName() {
        return projectRoleRepo.findAllByOrderByRoleName();
    }

    public List<String> findAllRoleNames() {
        List<String> existingRoles = new ArrayList<>();
        projectRoleRepo.findAll().forEach(role -> existingRoles.add(role.getRoleName()));
        return existingRoles;
    }

    public void addNewProjectRole(ProjectRole projectRole) {
        projectRoleRepo.save(projectRole);
    }

    public void deleteProjectRole(ProjectRole projectRole) {
        projectRoleRepo.delete(projectRole);
    }
}
