package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Value;
import pl.kozhanov.ProjectManagementSystem.domain.Comment;
import pl.kozhanov.ProjectManagementSystem.domain.ProjectStatus;
import pl.kozhanov.ProjectManagementSystem.domain.UserProjectRoleLink;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


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

     List<Comment> getComments();


    default  List<Comment> getSortedComments(){
        List<Comment> sortedList = getComments().stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                .collect(Collectors.toList());
        return  sortedList;
    }
}
