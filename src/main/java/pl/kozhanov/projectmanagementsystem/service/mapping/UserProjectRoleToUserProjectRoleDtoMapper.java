package pl.kozhanov.projectmanagementsystem.service.mapping;

import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import pl.kozhanov.projectmanagementsystem.domain.UserProjectRoleLink;
import pl.kozhanov.projectmanagementsystem.dto.UserProjectRoleDto;

@Component
public class UserProjectRoleToUserProjectRoleDtoMapper extends DefaultCustomMapper<UserProjectRoleLink, UserProjectRoleDto> {

    @Override
    public void mapAtoB(UserProjectRoleLink userProjectRoleLink, UserProjectRoleDto userProjectRoleDto, MappingContext mappingContext){
        userProjectRoleDto.setUserProjectRoleId(userProjectRoleLink.getId());
        userProjectRoleDto.setProjectId(userProjectRoleLink.getProject().getId());
        userProjectRoleDto.setProjectRoleName(userProjectRoleLink.getProjectRoles().getRoleName());
        userProjectRoleDto.setUserId(userProjectRoleLink.getUser().getId());
        userProjectRoleDto.setUserName(userProjectRoleLink.getUser().getUsername());
    }
}
