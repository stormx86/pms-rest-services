package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.ProjectManagementSystem.domain.ProjectStatus;

public interface ProjectStatusRepo extends JpaRepository<ProjectStatus, Long> {

    ProjectStatus findByStatusName(String statusName);
}
