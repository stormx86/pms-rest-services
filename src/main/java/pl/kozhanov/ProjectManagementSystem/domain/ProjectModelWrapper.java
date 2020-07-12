package pl.kozhanov.ProjectManagementSystem.domain;

import org.springframework.web.bind.annotation.RequestParam;
import pl.kozhanov.ProjectManagementSystem.service.validation.ProjectManagerConstraint;
import pl.kozhanov.ProjectManagementSystem.service.validation.UserMemberConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class ProjectModelWrapper {

    private Integer projectId;
    @NotBlank(message = "Title field can't be empty")
    private String title;
    @NotBlank(message = "Description field can't be empty")
    private String description;
    @ProjectManagerConstraint
    @NotBlank(message = "Project Manager field can't be empty")
    private String projectManager;
    private List<String> roles;
    @UserMemberConstraint
    private List<String> users;
    private List<String> existingUsers;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getExistingUsers() {
        return existingUsers;
    }

    public void setExistingUsers(List<String> existingUsers) {
        this.existingUsers = existingUsers;
    }
}
