package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.ProjectManagementSystem.domain.ProjectRole;

import java.util.List;

public interface ProjectRoleRepo extends JpaRepository<ProjectRole, Long> {

    ProjectRole findByRoleName(String rolename);

    List<ProjectRole> findAll();
}
