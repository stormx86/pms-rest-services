package pl.kozhanov.projectmanagementsystem.domain;

import javax.persistence.*;

@Entity
public class UserProjectRoleLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectroles_id")
    private ProjectRole projectRole;


    public UserProjectRoleLink() {
    }

    public UserProjectRoleLink(User user, Project project, ProjectRole projectRole) {
        this.user = user;
        this.project = project;
        this.projectRole = projectRole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectRole getProjectRoles() {
        return projectRole;
    }

    public void setProjectRoles(ProjectRole projectRoles) {
        this.projectRole = projectRoles;
    }
}
