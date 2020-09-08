package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kozhanov.ProjectManagementSystem.domain.Project;
import pl.kozhanov.ProjectManagementSystem.service.ProjectViewProjection;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {

        List<Project> findAll();

        ProjectViewProjection findById(Integer id);

        Project getById(Integer id);

        @Query("SELECT p.id as id, p.createdAt as createdAt, p.title as title, p.status as status, p.projectManager as projectManager, p.creator as creator " +
                "FROM Project p ")
        Page<ProjectViewProjection> getAll(Pageable pageable);

        Page<ProjectViewProjection> findAllByCreator(String creator, Pageable pageable);

        Page<ProjectViewProjection> findAllByProjectManager(String projectManager, Pageable pageable);

        Page<ProjectViewProjection> findAllByProjectManagerAndCreator(String projectManager, String creator, Pageable pageable);

        @Query("SELECT p.id as id, p.createdAt as createdAt, p.title as title, p.status as status, p.projectManager as projectManager, p.creator as creator " +
                        "FROM Project p, Project p2 " +
                        "JOIN p2.userProjectRoleLink uprl2 " +
                        "WHERE p.projectManager = ?2 OR p.creator = ?2 OR uprl2.user.id=?1 AND uprl2.project.id=p.id group by p.id order by p.createdAt desc")
        Page<ProjectViewProjection> findAllWhereUserIsMember(Integer userId, String username, Pageable pageable);


        @Query("SELECT p.id as id, p.createdAt as createdAt, p.title as title, p.status as status, p.projectManager as projectManager, p.creator as creator " +
                "FROM Project p, Project p2 " +
                "JOIN p2.userProjectRoleLink uprl2 " +
                "WHERE p.creator = ?3 AND p.creator = ?2 OR p.creator = ?3 AND p.projectManager=?2 OR p.creator = ?3 AND uprl2.user.id=?1 AND uprl2.project.id=p.id group by p.id order by p.createdAt desc")
        Page<ProjectViewProjection> findAllWhereUserIsMemberByCreator(Integer userId, String username, String creator, Pageable pageable);


        @Query("SELECT p.id as id, p.createdAt as createdAt, p.title as title, p.status as status, p.projectManager as projectManager, p.creator as creator " +
                "FROM Project p, Project p2 " +
                "JOIN p2.userProjectRoleLink uprl2 " +
                "WHERE p.projectManager = ?3 AND p.projectManager = ?2 OR p.projectManager = ?3 AND p.creator=?2 OR p.projectManager = ?3 AND uprl2.user.id=?1 AND uprl2.project.id=p.id group by p.id order by p.createdAt desc")
        Page<ProjectViewProjection> findAllWhereUserIsMemberByProjectManager(Integer userId, String username, String projectManager, Pageable pageable);


        @Query("SELECT p.id as id, p.createdAt as createdAt, p.title as title, p.status as status, p.projectManager as projectManager, p.creator as creator " +
                "FROM Project p, Project p2 " +
                "JOIN p2.userProjectRoleLink uprl2 " +
                "WHERE p.projectManager = ?3 AND p.creator = ?4 AND p.projectManager= ?2 " +
                "OR p.projectManager = ?3 AND p.creator = ?4 AND p.creator= ?2 " +
                "OR p.projectManager = ?3 AND p.creator = ?4 AND uprl2.user.id=?1 AND uprl2.project.id=p.id group by p.id order by p.createdAt desc")
        Page<ProjectViewProjection> findAllWhereUserIsMemberByProjectManagerAndByCreator(Integer userId, String username, String projectManager, String creator, Pageable pageable);
}



