package pl.kozhanov.projectmanagementsystem.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime createdAt;
    private String title;
    private String description;
    private String creator;
    private String projectManager;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="status_id")
    private ProjectStatus status;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval= true)
    private Set<UserProjectRoleLink> userProjectRoleLink;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Project(LocalDateTime createdAt, String title, String description, String creator, String projectManager,
                   ProjectStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.creator = creator;
        this.projectManager = projectManager;
    }

    public Project() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Set<UserProjectRoleLink> getUserProjectRoleLink() {
        return userProjectRoleLink;
    }

    public void setUserProjectRoleLink(Set<UserProjectRoleLink> userProjectRoleLink) {
        this.userProjectRoleLink = userProjectRoleLink;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
