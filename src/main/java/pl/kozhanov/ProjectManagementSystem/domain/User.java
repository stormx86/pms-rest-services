package pl.kozhanov.ProjectManagementSystem.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private boolean active;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserProjectRoleLink> userProjectRoleLink;

    @OneToMany(mappedBy = "user")
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGlobalRoles();
    }
}
