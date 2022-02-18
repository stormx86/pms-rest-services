package pl.kozhanov.projectmanagementsystem.domain;

import pl.kozhanov.projectmanagementsystem.service.validation.NewUserConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Username field can't be empty")
    @NewUserConstraint
    private String username;
    private String password;
    private boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval= true)
    private Set<UserProjectRoleLink> userProjectRoleLink;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval= true)
    private List<Comment> comments;

    @ElementCollection(targetClass = GlobalRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_global_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<GlobalRole> globalRoles;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<UserProjectRoleLink> getUserProjectRoleLink() {
        return userProjectRoleLink;
    }

    public void setUserProjectRoleLink(Set<UserProjectRoleLink> userProjectRoleLink) {
        this.userProjectRoleLink = userProjectRoleLink;
    }

    public Set<GlobalRole> getGlobalRoles() {
        return globalRoles;
    }

    public void setGlobalRoles(Set<GlobalRole> globalRoles) {
        this.globalRoles = globalRoles;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
