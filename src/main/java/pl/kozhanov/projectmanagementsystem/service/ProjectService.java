package pl.kozhanov.projectmanagementsystem.service;

import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    List<Project> findAll();

    ProjectDto findProjectById(Integer projectId);

    List<ProjectDto> findProjects();

    Integer createProject(ProjectDto newProjectDto);

    Integer updateProject(Integer projectId, ProjectDto newProjectDto);

    void deleteProject(Integer projectId);
}