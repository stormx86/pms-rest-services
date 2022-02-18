package pl.kozhanov.projectmanagementsystem.service.mapping;

import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;
import pl.kozhanov.projectmanagementsystem.dto.ProjectRoleDto;

@Component
public class ProjectRoleToProjectRoleDtoMapper extends DefaultCustomMapper<ProjectRole, ProjectRoleDto> {

    @Override
    public void mapAtoB(ProjectRole projectRole, ProjectRoleDto projectRoleDto, MappingContext context) {
        projectRoleDto.setProjectRoleId(projectRole.getId());
        projectRoleDto.setRoleName(projectRole.getRoleName());
    }
}
