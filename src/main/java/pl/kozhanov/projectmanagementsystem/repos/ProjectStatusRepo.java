package pl.kozhanov.projectmanagementsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.projectmanagementsystem.domain.ProjectStatus;

import java.util.List;

public interface ProjectStatusRepo extends JpaRepository<ProjectStatus, Long> {

    ProjectStatus findByStatusName(String statusName);

    List<ProjectStatus> findAll();
}
