package pl.kozhanov.ProjectManagementSystem.service;

import pl.kozhanov.ProjectManagementSystem.domain.ProjectStatus;
import pl.kozhanov.ProjectManagementSystem.domain.UserProjectRoleLink;

import java.time.LocalDateTime;
import java.util.*;

public interface ProjectView {

    Integer getId();
    LocalDateTime getCreatedAt();
    String getTitle();
    String getDescription();
    ProjectStatus getStatus();
    Set<UserProjectRoleLink> getUserProjectRoleLink();

    default List<String> getRoleUser(){
        List<String> roleUserList = new ArrayList<>();
        getUserProjectRoleLink().stream().forEach(a -> roleUserList.add(a.getProjectRoles().getRoleName() + ":" + a.getUser().getUsername()));
        Collections.sort(roleUserList);
        return roleUserList;
    }

}
