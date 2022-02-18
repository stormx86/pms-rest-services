package pl.kozhanov.projectmanagementsystem.service.mapping;

import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import pl.kozhanov.projectmanagementsystem.domain.ProjectStatus;
import pl.kozhanov.projectmanagementsystem.dto.ProjectStatusDto;

@Component
public class ProjectStatusToProjectStatusDtoMapper extends DefaultCustomMapper<ProjectStatus, ProjectStatusDto> {

    @Override
    public void mapAtoB(ProjectStatus projectStatus, ProjectStatusDto projectStatusDto, MappingContext context) {
        projectStatusDto.setCode(projectStatus.getCode());
        projectStatusDto.setStatusName(projectStatus.name());
    }
}
