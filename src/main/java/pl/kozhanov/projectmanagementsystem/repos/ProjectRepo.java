package pl.kozhanov.projectmanagementsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kozhanov.projectmanagementsystem.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project, Long> {

        List<Project> findAll();

        Optional<Project> findById(Integer id);

        Project getById(Integer id);

}



