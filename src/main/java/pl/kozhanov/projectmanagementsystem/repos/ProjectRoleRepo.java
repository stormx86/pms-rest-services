package pl.kozhanov.projectmanagementsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;

public interface ProjectRoleRepo extends JpaRepository<ProjectRole, Integer> {

    ProjectRole findByRoleName(String rolename);
}
