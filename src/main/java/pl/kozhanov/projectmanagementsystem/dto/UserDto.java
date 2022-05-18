package pl.kozhanov.projectmanagementsystem.dto;

import pl.kozhanov.projectmanagementsystem.domain.GlobalRole;
import pl.kozhanov.projectmanagementsystem.service.validation.ChangeUserPasswordConstraint;
import pl.kozhanov.projectmanagementsystem.service.validation.NewUserConstraint;
import pl.kozhanov.projectmanagementsystem.service.validation.groups.ValidationGroups;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@ChangeUserPasswordConstraint(groups = ValidationGroups.ChangeUserPassword.class)
public class UserDto {

    private Integer userId;

    @NotBlank(groups = ValidationGroups.NewUser.class, message = "Field is required")
    @NewUserConstraint(groups = ValidationGroups.NewUser.class)
    private String username;

    @NotBlank(groups = ValidationGroups.ChangeUserPassword.class, message = "Field is required")
    private String password;

    @NotBlank(groups = ValidationGroups.ChangeUserPassword.class, message = "Field is required")
    private String repeatPassword;

    private Set<GlobalRole> globalRoles;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public Set<GlobalRole> getGlobalRoles() {
        return globalRoles;
    }

    public void setGlobalRoles(Set<GlobalRole> globalRoles) {
        this.globalRoles = globalRoles;
    }
}
