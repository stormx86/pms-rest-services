package pl.kozhanov.ProjectManagementSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.kozhanov.ProjectManagementSystem.domain.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAll();

    //usernames for autocomplete
    @Query("select u.username from User u where u.username LIKE %:term%")
    List<String> findByUsernameLike(String term);
}
