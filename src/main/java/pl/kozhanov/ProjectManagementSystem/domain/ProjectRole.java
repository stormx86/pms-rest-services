package pl.kozhanov.ProjectManagementSystem.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ProjectRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String roleName;
    @OneToMany(mappedBy = "projectRole", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
