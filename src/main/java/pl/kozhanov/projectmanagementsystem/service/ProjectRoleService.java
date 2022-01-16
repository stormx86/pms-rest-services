package pl.kozhanov.projectmanagementsystem.service;

import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;

import java.util.List;

public interface ProjectRoleService {

    ProjectRole findByRoleName(String rolename);

    List<ProjectRole> findAllByOrderByRoleName();

    List<String> findAllRoleNames();

    void addNewProjectRole(ProjectRole projectRole);

    void deleteProjectRole(ProjectRole projectRole);
}
