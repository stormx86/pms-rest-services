package pl.kozhanov.projectmanagementsystem.service;

import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;
import pl.kozhanov.projectmanagementsystem.dto.ProjectRoleDto;

import java.util.List;

public interface ProjectRoleService {

    ProjectRole findByRoleName(String rolename);

    List<ProjectRole> findAllByOrderByRoleName();

    List<ProjectRoleDto> findAllRoleNames();
}
