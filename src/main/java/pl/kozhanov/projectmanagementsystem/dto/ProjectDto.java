package pl.kozhanov.projectmanagementsystem.dto;

import pl.kozhanov.projectmanagementsystem.service.validation.ProjectManagerConstraint;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class ProjectDto {

    private Integer projectId;

    private String createdAt;

    @Size(min = 5, message = "Title must be at least 5 characters long")
    private String title;

    @Size(min = 5, message = "Description must be at least 5 characters long")
    private String description;

    private String creator;

    @ProjectManagerConstraint
    private String projectManager;

    private String status;

    @Valid
    private Set<UserProjectRoleDto> userProjectRoleDto;

    private List<CommentDto> comments;

    public Integer getProjectId() { return projectId; }

    public void setProjectId(Integer projectId) { this.projectId = projectId; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getCreator() { return creator; }

    public void setCreator(String creator) { this.creator = creator; }

    public String getProjectManager() { return projectManager; }

    public void setProjectManager(String projectManager) { this.projectManager = projectManager; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Set<UserProjectRoleDto> getUserProjectRoleDto() {
        return userProjectRoleDto;
    }

    public void setUserProjectRoleDto(Set<UserProjectRoleDto> userProjectRoleDto) {
        this.userProjectRoleDto = userProjectRoleDto;
    }

    public List<CommentDto> getComments() { return comments; }

    public void setComments(List<CommentDto> comments) { this.comments = comments; }
}
