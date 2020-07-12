package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kozhanov.ProjectManagementSystem.domain.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User getById(Integer id);

    List<User> findAll();

    //usernames for autocomplete
    @Query("select u.username from User u where u.username LIKE %:term%")
    List<String> findByUsernameLike(String term);


    @Query( nativeQuery = true,
            value = "SELECT u.username " +
                    "FROM usr as u " +
                    "JOIN user_project_role_link as uprl on uprl.user_id = u.id " +
                    "JOIN project as p on p.id = uprl.project_id " +
                    "WHERE p.id=:projectId")
    List<String> findAllUsersOnProject(@Param("projectId") Integer projectId);



}
