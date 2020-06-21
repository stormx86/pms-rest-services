package pl.kozhanov.ProjectManagementSystem.service;

import pl.kozhanov.ProjectManagementSystem.domain.UserProjectRoleLink;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface ProjectView {

    Integer getId();
    LocalDateTime getCreatedAt();
    String getTitle();
    String getDescription();
    String getStatus();
    Set<UserProjectRoleLink> getUserProjectRoleLink();

    default Map<String, String> getRoleUser(){
        Map<String, String> map_ru = new HashMap<>();
        getUserProjectRoleLink().stream().forEach(a -> map_ru.put(a.getProjectRoles().getRoleName(), a.getUser().getUsername()));
        return map_ru;
    }

}
