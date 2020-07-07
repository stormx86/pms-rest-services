package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.ProjectManagementSystem.domain.ProjectRole;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ProjectRoleRepo extends JpaRepository<ProjectRole, Integer> {

    ProjectRole findByRoleName(String rolename);

    List<ProjectRole> findAllByOrderByRoleName();

}
