package pl.kozhanov.projectmanagementsystem.service;

import pl.kozhanov.projectmanagementsystem.dto.ProjectRoleDto;

import java.util.List;

public interface ProjectRoleService {

    List<ProjectRoleDto> findAllRoleNames();

    List<ProjectRoleDto> addNewProjectRole(ProjectRoleDto projectRoleDto);

    List<ProjectRoleDto> deleteProjectRole(Integer projectRoleId);
}
