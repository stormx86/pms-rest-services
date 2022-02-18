package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.dto.ProjectDto;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;
import pl.kozhanov.projectmanagementsystem.service.ProjectService;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepo projectRepo;
    private final OrikaBeanMapper mapper;

    public ProjectServiceImpl(final ProjectRepo projectRepo,
                              final OrikaBeanMapper mapper) {
        this.projectRepo = projectRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public List<Project> findAll() {
        return projectRepo.findAll();
    }

    @Override
    @Transactional
    public ProjectDto findProjectById(final Integer projectId) {

        final Project project = projectRepo.findById(projectId).orElseThrow(() -> new EntityNotFoundException(
                        "Project with id " + projectId + "was not found"));

        return mapper.map(project, ProjectDto.class);
    }

    @Override
    @Transactional
    public List<ProjectDto> findProjects() {
        final List<ProjectDto> projects = projectRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(Project::getCreatedAt).reversed())
                .map(project -> mapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());

        return projects;
    }

    @Override
    @Transactional
    public Integer createProject(final ProjectDto newProjectDto){
        final Project createdProject = mapper.map(newProjectDto, Project.class);
        projectRepo.save(createdProject);

        return createdProject.getId();
    }

    @Override
    @Transactional
    public Integer updateProject(final Integer projectId, final ProjectDto projectForUpdateDto){
        final Project existingProject = projectRepo.findById(projectId).orElseThrow(() -> new EntityNotFoundException(
                "Project with id " + projectId + "was not found"));
        final Project updatedProject = mapper.map(projectForUpdateDto, Project.class);

        existingProject.setProjectManager(updatedProject.getProjectManager());
        existingProject.setTitle(updatedProject.getTitle());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.getUserProjectRoleLink().clear();
        updatedProject.getUserProjectRoleLink().forEach(rolesCollection-> existingProject.getUserProjectRoleLink().add(rolesCollection));
        projectRepo.save(existingProject);

        return existingProject.getId();
    }

    @Override
    @Transactional
    public void deleteProject(final Integer projectId) {
        final Project project = projectRepo.findById(projectId).orElseThrow(() -> new EntityNotFoundException(
                "Project with id " + projectId + "was not found"));

        projectRepo.delete(project);
    }
}