package pl.kozhanov.projectmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kozhanov.projectmanagementsystem.domain.GlobalRole;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.repos.UserRepo;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProjectService projectService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Page<User> findAllByOrderByUsernameAsc(Pageable pageable) {
        return userRepo.findAllByOrderByUsernameAsc(pageable);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }


    //usernames for autocomplete
    public List<String> findByUsernameLike(String term) {
        return userRepo.findByUsernameLike(term);
    }

    public List<String> findAllUsersOnProject(Integer projectId) {
        return userRepo.findAllUsersOnProject(projectId);
    }

    public String getCurrentLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
    }

    public boolean isCreator(String currentLoggedInUser, Integer projectId) {
        return projectService.findById(projectId).getCreator().equals(currentLoggedInUser);
    }

    public boolean isProjectManager(String currentLoggedInUser, Integer projectId) {
        return projectService.findById(projectId).getProjectManager().equals(currentLoggedInUser);
    }

    public boolean hasProjectAuthorities(String currentLoggedInUser, Integer projectId) {
        return !(!findAllUsersOnProject(projectId).contains(currentLoggedInUser) &&
                !isAdmin() &&
                !isCreator(currentLoggedInUser, projectId) &&
                !isProjectManager(currentLoggedInUser, projectId));
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getUsername()));
        user.setActive(true);
        user.setGlobalRoles(Collections.singleton(GlobalRole.USER));
        userRepo.save(user);
    }

    public String saveUser(Integer userId, String newUsername, String[] roles) {
        for (User u : userRepo.findAll()) {
            //if newUsername is in userDB & newUsername != username of current userID
            if (u.getUsername().equals(newUsername) && !u.getUsername().equals(userRepo.getById(userId).getUsername()))
                return "Username already exists";
            else if (newUsername.equals("")) return "Username field can't be empty";
        }

        User user = userRepo.getById(userId);
        user.setUsername(newUsername);
        user.getGlobalRoles().clear();

        IntStream.range(0, roles.length).forEach(i -> user.getGlobalRoles().add(GlobalRole.valueOf(roles[i])));
        userRepo.save(user);
        return "Successfully saved!";
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }


    public void changeUserPassword(String username, String password) {
        User user = userRepo.findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
    }

    public void resetUserPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getUsername()));
        userRepo.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
