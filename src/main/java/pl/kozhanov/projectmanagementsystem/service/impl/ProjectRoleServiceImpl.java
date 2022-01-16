package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.stereotype.Service;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRoleRepo;
import pl.kozhanov.projectmanagementsystem.service.ProjectRoleService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectRoleServiceImpl implements ProjectRoleService {

    private final ProjectRoleRepo projectRoleRepo;

    public ProjectRoleServiceImpl(final ProjectRoleRepo projectRoleRepo) {
        this.projectRoleRepo = projectRoleRepo;
    }

    public ProjectRole findByRoleName(String rolename) {
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
