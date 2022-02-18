package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;
import pl.kozhanov.projectmanagementsystem.dto.ProjectRoleDto;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRoleRepo;
import pl.kozhanov.projectmanagementsystem.service.ProjectRoleService;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ProjectRoleDto> findAllRoleNames() {
        final List<ProjectRoleDto> projectRoles = projectRoleRepo.findAll()
                .stream()
                .map(role -> mapper.map(role, ProjectRoleDto.class))
                .collect(Collectors.toList());

        return projectRoles;
    }
}
