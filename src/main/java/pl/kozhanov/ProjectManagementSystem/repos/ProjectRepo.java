package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.ProjectManagementSystem.domain.Project;
import pl.kozhanov.ProjectManagementSystem.service.ProjectView;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {

        List<Project> findAll();

        ProjectView findById(Integer id);

        Project getById(Integer id);

        List<ProjectView> findAllByOrderByCreatedAtDesc();


}



