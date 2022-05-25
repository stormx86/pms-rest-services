package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kozhanov.projectmanagementsystem.domain.ProjectStatus;
import pl.kozhanov.projectmanagementsystem.dto.NewProjectStatusRequestDto;
import pl.kozhanov.projectmanagementsystem.dto.ProjectStatusDto;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;
import pl.kozhanov.projectmanagementsystem.service.ProjectStatusService;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProjectStatusServiceImpl implements ProjectStatusService {

    private final ProjectRepo projectRepo;
    private final OrikaBeanMapper mapper;

    public ProjectStatusServiceImpl(final ProjectRepo projectRepo,
                                    final OrikaBeanMapper mapper) {
        this.projectRepo = projectRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public List<ProjectStatusDto> findAllStatuses() {
        final List<ProjectStatus> projectStatusList = Arrays.asList(ProjectStatus.values());

        return projectStatusList.stream().map(projectStatus -> mapper.map(projectStatus, ProjectStatusDto.class))
                .collect(toList());
    }

    @Override
    @Transactional
    public String setNewProjectStatus(final NewProjectStatusRequestDto newProjectStatusRequestDto) {
        projectRepo.findById(newProjectStatusRequestDto.getProjectId())
                .ifPresent(project -> project.setStatus(ProjectStatus.valueOf(newProjectStatusRequestDto.getNewStatus())));
        return newProjectStatusRequestDto.getNewStatus();
    }
}
