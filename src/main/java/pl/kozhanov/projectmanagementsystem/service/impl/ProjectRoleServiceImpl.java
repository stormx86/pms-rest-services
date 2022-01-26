package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    @Transactional
    public ProjectRole findByRoleName(final String roleName) {
        return projectRoleRepo.findByRoleName(roleName);
    }

    @Override
    @Transactional
    public List<ProjectRole> findAllByOrderByRoleName() {
        return projectRoleRepo.findAllByOrderByRoleName();
    }

    @Override
    @Transactional
    public List<String> findAllRoleNames() {
        List<String> existingRoles = new ArrayList<>();
        projectRoleRepo.findAll().forEach(role -> existingRoles.add(role.getRoleName()));
        return existingRoles;
    }

    @Override
    @Transactional
    public void addNewProjectRole(final ProjectRole projectRole) {
        projectRoleRepo.save(projectRole);
    }

    @Override
    @Transactional
    public void deleteProjectRole(final ProjectRole projectRole) {
        projectRoleRepo.delete(projectRole);
    }
}
