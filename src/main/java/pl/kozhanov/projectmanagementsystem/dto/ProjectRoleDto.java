package pl.kozhanov.projectmanagementsystem.dto;

public class ProjectRoleDto {

    private Integer projectRoleId;

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
