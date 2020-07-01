package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.ProjectManagementSystem.domain.Project;
import pl.kozhanov.ProjectManagementSystem.service.ProjectViewProjection;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {

        List<Project> findAll();

        ProjectViewProjection findById(Integer id);

        Project getById(Integer id);

        List<ProjectViewProjection> findAllByOrderByCreatedAtDesc();


}



