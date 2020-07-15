package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        Page<ProjectViewProjection> findAllByOrderByCreatedAtDesc(Pageable pageable);

        Page<ProjectViewProjection> findAllByCreatorOrderByCreatedAtDesc(String creator, Pageable pageable);

        Page<ProjectViewProjection> findAllByProjectManagerOrderByCreatedAtDesc(String projectManager, Pageable pageable);

        Page<ProjectViewProjection> findAllByProjectManagerAndCreatorOrderByCreatedAtDesc(String projectManager, String creator, Pageable pageable);

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




       /* @Query("SELECT p FROM ")
        List<ProjectViewProjection> findByPmAndCreator(String pmName, String creatorName);


        @Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
        User findUserByStatusAndName(Integer status, String name);*/

/*    @Query( nativeQuery = true,
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
                                                               @Param("createdByFilter") String createdByFilter);*/
}



