package pl.kozhanov.projectmanagementsystem.service;

import pl.kozhanov.projectmanagementsystem.domain.Comment;
import pl.kozhanov.projectmanagementsystem.domain.ProjectStatus;
import pl.kozhanov.projectmanagementsystem.domain.UserProjectRoleLink;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public interface ProjectViewProjection {

    Integer getId();

    Instant getCreatedAt();

    String getTitle();

    String getDescription();

    ProjectStatus getStatus();

    String getCreator();

    String getProjectManager();

    Set<UserProjectRoleLink> getUserProjectRoleLink();

    default String getCreatedAtFormatted() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(ZonedDateTime.ofInstant(getCreatedAt(), ZoneId.systemDefault()));
    }

    default List<String> getRoleUser() {
        List<String> roleUserList = new ArrayList<>();
        getUserProjectRoleLink().stream().forEach(a -> roleUserList.add(a.getProjectRoles().getRoleName() + ":" + a.getUser().getUsername()));
        Collections.sort(roleUserList);
        return roleUserList;
    }

    List<Comment> getComments();

    default List<Comment> getSortedComments() {
        return getComments().stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }


}
