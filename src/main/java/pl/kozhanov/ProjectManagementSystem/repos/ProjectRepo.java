package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kozhanov.ProjectManagementSystem.domain.Project;
import pl.kozhanov.ProjectManagementSystem.service.ProjectMainProjection;
import pl.kozhanov.ProjectManagementSystem.service.ProjectViewProjection;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {

        List<Project> findAll();

        ProjectViewProjection findById(Integer id);

        Project getById(Integer id);

        List<ProjectViewProjection> findAllByOrderByCreatedAtDesc();

        List<ProjectViewProjection> findAllByCreatorOrderByCreatedAtDesc(String creator);

        List<ProjectViewProjection> findAllByProjectManagerOrderByCreatedAtDesc(String projectManager);

        List<ProjectViewProjection> findAllByProjectManagerAndCreatorOrderByCreatedAtDesc(String projectManager, String creator);


       /* @Query("SELECT p FROM ")
        List<ProjectViewProjection> findByPmAndCreator(String pmName, String creatorName);


        @Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
        User findUserByStatusAndName(Integer status, String name);*/

    @Query( nativeQuery = true,
            value = "SELECT p.id as id, p.created_at as createdAt, p.title as title, s.status_name as status, u.username as pmName, " +
                    "(SELECT u2.username from usr u2 join user_project_role_link l on u2.id = l.user_id join project_role r on l.projectroles_id = r.id where l.project_id = p.id and r.role_name='Creator') as creatorName " +
                    "FROM project as p " +
                    "JOIN project_status as s on s.id = p.status_id " +
                    "JOIN user_project_role_link as uprl on uprl.project_id=p.id " +
                    "JOIN usr as u on u.id = uprl.user_id " +
                    "JOIN project_role as pr on pr.id = uprl.projectroles_id " +
                    "WHERE pr.id=1 "+
                    "order by p.created_at desc ")
       List<ProjectMainProjection> findAllForMainList();


    @Query( nativeQuery = true,
            value = "SELECT p.id as id, p.created_at as createdAt, p.title as title, s.status_name as status, u.username as pmName, " +
                    "(SELECT u2.username from usr u2 join user_project_role_link l on u2.id = l.user_id join project_role r on l.projectroles_id = r.id where l.project_id = p.id and r.id=0) as creatorName " +
                    "FROM project as p " +
                    "JOIN project_status as s on s.id = p.status_id " +
                    "JOIN user_project_role_link as uprl on uprl.project_id=p.id " +
                    "JOIN usr as u on u.id = uprl.user_id " +
                    "JOIN project_role as pr on pr.id = uprl.projectroles_id " +
                    "WHERE pr.id=1 and u.username=:projectManagerFilter " +
                    "order by p.created_at desc ")
    List<ProjectMainProjection> findByProjectManager(@Param("projectManagerFilter") String projectManagerFilter);

    @Query( nativeQuery = true,
            value = "SELECT p.id as id, p.created_at as createdAt, p.title as title, s.status_name as status, u.username as creatorName, " +
                    "(SELECT u2.username from usr u2 join user_project_role_link l on u2.id = l.user_id join project_role r on l.projectroles_id = r.id where l.project_id = p.id and r.id=1) as pmName  " +
                    "FROM project as p " +
                    "JOIN project_status as s on s.id = p.status_id " +
                    "JOIN user_project_role_link as uprl on uprl.project_id=p.id " +
                    "JOIN usr as u on u.id = uprl.user_id " +
                    "JOIN project_role as pr on pr.id = uprl.projectroles_id " +
                    "WHERE pr.id=0 and u.username=:createdByFilter " +
                    "order by p.created_at desc ")
    List<ProjectMainProjection> findByCreator(@Param("createdByFilter") String createdByFilter);


    @Query( nativeQuery = true,
            value = "SELECT p.id as id, p.created_at as createdAt, p.title as title, s.status_name as status, u.username as pmName, " +
                    "(SELECT u2.username from usr u2 join user_project_role_link l on u2.id = l.user_id join project_role r on l.projectroles_id = r.id where l.project_id = p.id and r.id=0 and u2.username=:createdByFilter) as creatorName " +
                    "FROM project as p " +
                    "JOIN project_status as s on s.id = p.status_id " +
                    "JOIN user_project_role_link as uprl on uprl.project_id=p.id " +
                    "JOIN usr as u on u.id = uprl.user_id " +
                    "JOIN project_role as pr on pr.id = uprl.projectroles_id " +
                    "WHERE pr.id=1 and u.username=:projectManagerFilter and (SELECT u2.username from usr u2 join user_project_role_link l on u2.id = l.user_id join project_role r on l.projectroles_id = r.id where l.project_id = p.id and r.id=0 and u2.username=:createdByFilter) is not null " +
                    "order by p.created_at desc ")
    List<ProjectMainProjection> findByProjectManagerAndCreator(@Param("projectManagerFilter") String projectManagerFilter,
                                                               @Param("createdByFilter") String createdByFilter);
}



