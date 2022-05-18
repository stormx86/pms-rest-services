package pl.kozhanov.projectmanagementsystem.dto;

import pl.kozhanov.projectmanagementsystem.service.validation.NewProjectRoleConstraint;
import pl.kozhanov.projectmanagementsystem.service.validation.groups.ValidationGroups;

import javax.validation.constraints.NotBlank;

public class ProjectRoleDto {

    private Integer projectRoleId;

    @NotBlank(groups = ValidationGroups.NewProjectRole.class, message = "Field is required")
    @NewProjectRoleConstraint(groups = ValidationGroups.NewProjectRole.class)
    private String roleName;

    public Integer getProjectRoleId() {
        return projectRoleId;
    }

    public void setProjectRoleId(Integer projectRoleId) {
        this.projectRoleId = projectRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
