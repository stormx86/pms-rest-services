package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;
import pl.kozhanov.projectmanagementsystem.dto.ProjectRoleDto;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRoleRepo;
import pl.kozhanov.projectmanagementsystem.service.ProjectRoleService;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ProjectRoleServiceImpl implements ProjectRoleService {

    private final ProjectRoleRepo projectRoleRepo;
    private final OrikaBeanMapper mapper;

    public ProjectRoleServiceImpl(final ProjectRoleRepo projectRoleRepo,
                                  final OrikaBeanMapper mapper) {
        this.projectRoleRepo = projectRoleRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public List<ProjectRoleDto> findAllRoleNames() {
        final List<ProjectRoleDto> projectRoles = projectRoleRepo.findAll()
                .stream()
                .map(role -> mapper.map(role, ProjectRoleDto.class))
                .collect(Collectors.toList());

        return projectRoles;
    }

    @Override
    @Transactional
    public List<ProjectRoleDto> addNewProjectRole(final ProjectRoleDto projectRoleDto) {
        final ProjectRole newRole = mapper.map(projectRoleDto, ProjectRole.class);
        projectRoleRepo.save(newRole);

        return projectRoleRepo.findAll()
                .stream()
                .map(role-> mapper.map(role, ProjectRoleDto.class))
                .collect(toList());
    }

    @Override
    @Transactional
    public List<ProjectRoleDto> deleteProjectRole(final Integer projectRoleId){
        final ProjectRole projectRoleForDelete =  projectRoleRepo.findById(projectRoleId)
                .orElseThrow(() -> new EntityNotFoundException("Project Role not found with id: " + projectRoleId));
        projectRoleRepo.delete(projectRoleForDelete);

        return projectRoleRepo.findAll()
                .stream()
                .map(role-> mapper.map(role, ProjectRoleDto.class))
                .collect(toList());
    }
}
