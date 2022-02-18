package pl.kozhanov.projectmanagementsystem.service.mapping;

import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import pl.kozhanov.projectmanagementsystem.domain.Project;
import pl.kozhanov.projectmanagementsystem.domain.ProjectStatus;
import pl.kozhanov.projectmanagementsystem.domain.UserProjectRoleLink;
import pl.kozhanov.projectmanagementsystem.dto.CommentDto;
import pl.kozhanov.projectmanagementsystem.dto.ProjectDto;
import pl.kozhanov.projectmanagementsystem.dto.UserProjectRoleDto;
import pl.kozhanov.projectmanagementsystem.repos.CommentRepo;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRoleRepo;
import pl.kozhanov.projectmanagementsystem.repos.UserRepo;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class ProjectToProjectDtoMapper extends DefaultCustomMapper<Project, ProjectDto> {

    private final CommentRepo commentRepo;
    private final ProjectRoleRepo projectRoleRepo;
    private final UserRepo userRepo;

    public ProjectToProjectDtoMapper(final CommentRepo commentRepo,
                                     final ProjectRoleRepo projectRoleRepo,
                                     final UserRepo userRepo) {
        this.commentRepo = commentRepo;
        this.projectRoleRepo = projectRoleRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void mapAtoB(Project project, ProjectDto projectDto, MappingContext context) {
        projectDto.setProjectId(project.getId());
        projectDto.setStatus(project.getStatus().name());
        projectDto.setComments(getComments(project));
        projectDto.setUserProjectRoleDto(getUserProjectRoleDto(project));
    }

    @Override
    public void mapBtoA(ProjectDto projectDto, Project project, MappingContext context) {
        if(projectDto.getProjectId() != null){
            project.setId(projectDto.getProjectId());
        }
        project.setCreatedAt(Instant.now());
        project.setTitle(projectDto.getTitle());
        project.setDescription(projectDto.getDescription());
        project.setCreator(projectDto.getCreator());
        project.setProjectManager(projectDto.getProjectManager());

        if (projectDto.getProjectId() != null) {
            project.setStatus(ProjectStatus.valueOf(projectDto.getStatus()));
            project.setComments(commentRepo.findAllByProjectId(projectDto.getProjectId()));
        } else {
            project.setStatus(ProjectStatus.WAITING);
        }

        project.setUserProjectRoleLink(createUserProjectRoleLink(project, projectDto.getUserProjectRoleDto()));
    }

    private Set<UserProjectRoleLink> createUserProjectRoleLink(final Project project, final Set<UserProjectRoleDto> userProjectRoleDtoSet){
        final Set<UserProjectRoleLink> userProjectRoleSet = new HashSet<>();

        userProjectRoleDtoSet.forEach(userProjectRoleDto -> {
            final UserProjectRoleLink userProjectRoleLink = new UserProjectRoleLink();
            userProjectRoleLink.setProject(project);
            userProjectRoleLink.setProjectRoles(projectRoleRepo.findByRoleName(userProjectRoleDto.getProjectRoleName()));
            userProjectRoleLink.setUser(userRepo.findByUsername(userProjectRoleDto.getUserName()).orElse(null));

            userProjectRoleSet.add(userProjectRoleLink);
        });

        return userProjectRoleSet;
    }

    private List<CommentDto> getComments(final Project project) {
        return project.getComments()
                .stream()
                .map(comment -> mapperFacade.map(comment, CommentDto.class))
                .collect(toList());
    }

    private Set<UserProjectRoleDto> getUserProjectRoleDto(final Project project) {
        return project.getUserProjectRoleLink()
                .stream()
                .map(role -> mapperFacade.map(role, UserProjectRoleDto.class))
                .collect(toSet());
    }
}
