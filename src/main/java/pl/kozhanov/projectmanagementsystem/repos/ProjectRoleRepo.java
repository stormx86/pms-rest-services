package pl.kozhanov.projectmanagementsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;

import java.util.List;

public interface ProjectRoleRepo extends JpaRepository<ProjectRole, Integer> {

    ProjectRole findByRoleName(String rolename);

    List<ProjectRole> findAllByOrderByRoleName();
}
