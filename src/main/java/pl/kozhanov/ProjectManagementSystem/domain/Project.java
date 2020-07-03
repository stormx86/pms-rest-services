package pl.kozhanov.ProjectManagementSystem.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Instant createdAt;
    private String title;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="status_id", referencedColumnName = "id")
    private ProjectStatus status;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval= true)
    private Set<UserProjectRoleLink> userProjectRoleLink;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.ALL})
    private List<Comment> comments;


    public Project(Instant createdAt, String title, String description, ProjectStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt;
    }
        /*DateTimeFormatter formatter = null;
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.createdAt = LocalDateTime.parse(createdAt.toString(), formatter);*/


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
