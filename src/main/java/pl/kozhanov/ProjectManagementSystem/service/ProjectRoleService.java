package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.ProjectRole;
import pl.kozhanov.ProjectManagementSystem.repos.ProjectRoleRepo;

@Service
public class ProjectRoleService {

    @Autowired
    ProjectRoleRepo projectRoleRepo;

    public ProjectRole findByRoleName(String rolename){ return projectRoleRepo.findByRoleName(rolename); }
}
