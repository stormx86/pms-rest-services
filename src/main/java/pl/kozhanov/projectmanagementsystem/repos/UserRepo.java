package pl.kozhanov.projectmanagementsystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kozhanov.projectmanagementsystem.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    List<User> findAll();

    List<User> findAllByOrderByUsernameAsc();

    //usernames for autocomplete
    @Query("SELECT u.username FROM User u WHERE u.username LIKE %:term%")
    List<String> findByUsernameLike(String term);
}
