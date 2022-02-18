package pl.kozhanov.projectmanagementsystem.domain;

import pl.kozhanov.projectmanagementsystem.service.validation.NewProjectRoleConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class ProjectRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Role Name field can't be empty")
    @NewProjectRoleConstraint
    private String roleName;

    @OneToMany(mappedBy = "projectRole", cascade = CascadeType.ALL, orphanRemoval= true)
    private Set<UserProjectRoleLink> userProjectRoleLink;

    public ProjectRole() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<UserProjectRoleLink> getUserProjectRoleLink() {
        return userProjectRoleLink;
    }

    public void setUserProjectRoleLink(Set<UserProjectRoleLink> userProjectRoleLink) {
        this.userProjectRoleLink = userProjectRoleLink;
    }
}
