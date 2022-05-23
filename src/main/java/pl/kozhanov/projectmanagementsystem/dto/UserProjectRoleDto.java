package pl.kozhanov.projectmanagementsystem.dto;

import pl.kozhanov.projectmanagementsystem.service.validation.ValidUsernameConstraint;

public class UserProjectRoleDto {

    private Integer userProjectRoleId;

    private Integer userId;

    @ValidUsernameConstraint
    private String userName;

    private Integer projectId;

    private String projectRoleName;

    public Integer getUserProjectRoleId() {
        return userProjectRoleId;
    }

    public void setUserProjectRoleId(Integer userProjectRoleId) {
        this.userProjectRoleId = userProjectRoleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectRoleName() {
        return projectRoleName;
    }

    public void setProjectRoleName(String projectRoleName) {
        this.projectRoleName = projectRoleName;
    }
}
