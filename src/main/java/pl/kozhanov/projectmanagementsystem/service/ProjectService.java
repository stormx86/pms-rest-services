package pl.kozhanov.projectmanagementsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.kozhanov.projectmanagementsystem.domain.Project;

import java.util.List;

public interface ProjectService {

    List<Project> findAll();

    ProjectViewProjection findById(Integer id);

    boolean addProject(String title, String description, String projectManager, List<String> roles, List<String> existingUsers);

    void saveProject(Integer id, String title, String description, String projectManager, List<String> roles,
                List<String> existingUsers);

    void deleteProject(Integer projectId);

    void changeProjectStatus(Integer id, String status);

    void addNewComment(Integer id, String commentText);

    Page<ProjectViewProjection> findProjects(String projectManagerFilter, String createdByFilter,
                                             Pageable pageable);

    Sort sortManage(Sort sort);

}
