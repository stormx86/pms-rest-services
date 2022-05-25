package pl.kozhanov.projectmanagementsystem.service;

import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.dto.ProjectDto;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ProjectService {

    List<Project> findAll();

    ProjectDto findProjectById(Integer projectId, Integer userId) throws AccessDeniedException;

    List<ProjectDto> findProjects(Integer userId);

    List<ProjectDto> getProjectsForAdmin();

    List<ProjectDto> getProjectsForMember(User user);

    Integer createProject(ProjectDto newProjectDto);

    Integer updateProject(Integer projectId, ProjectDto newProjectDto);

    void deleteProject(Integer projectId);
}