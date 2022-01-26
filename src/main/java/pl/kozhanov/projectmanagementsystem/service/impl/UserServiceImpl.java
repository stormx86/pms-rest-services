package pl.kozhanov.projectmanagementsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kozhanov.projectmanagementsystem.domain.GlobalRole;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.repos.UserRepo;
import pl.kozhanov.projectmanagementsystem.service.ProjectService;
import pl.kozhanov.projectmanagementsystem.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private ProjectService projectService;

    public UserServiceImpl(final UserRepo userRepo,
                           final PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setProjectService(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public Page<User> findAllByOrderByUsernameAsc(final Pageable pageable) {
        return userRepo.findAllByOrderByUsernameAsc(pageable);
    }

    @Override
    @Transactional
    public User findByUsername(final String username) {
        return userRepo.findByUsername(username);
    }

    //usernames for autocomplete
    @Override
    @Transactional
    public List<String> findByUsernameLike(final String term) {
        return userRepo.findByUsernameLike(term);
    }


    @Override
    public String getCurrentLoggedInUsername() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @Override
    public boolean isAdmin() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
    }

    @Override
    @Transactional
    public boolean hasProjectAuthorities(final String currentLoggedInUser, final Integer projectId) {
        return !(!findAllUsersOnProject(projectId).contains(currentLoggedInUser) &&
                !isAdmin() &&
                !isCreator(currentLoggedInUser, projectId) &&
                !isProjectManager(currentLoggedInUser, projectId));
    }

    @Override
    @Transactional
    public void addUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getUsername()));
        user.setActive(true);
        user.setGlobalRoles(Collections.singleton(GlobalRole.USER));
        userRepo.save(user);
    }

    @Override
    @Transactional
    public String saveUser(final Integer userId, final String newUsername, final String[] roles) {
        for (User u : userRepo.findAll()) {
            //if newUsername is in userDB & newUsername != username of current userID
            if (u.getUsername().equals(newUsername) && !u.getUsername().equals(userRepo.getById(userId).getUsername()))
                return "Username already exists";
            else if (newUsername.equals("")) return "Username field can't be empty";
        }

        final User user = userRepo.getById(userId);
        user.setUsername(newUsername);
        user.getGlobalRoles().clear();

        IntStream.range(0, roles.length).forEach(i -> user.getGlobalRoles().add(GlobalRole.valueOf(roles[i])));
        userRepo.save(user);
        return "Successfully saved!";
    }

    @Override
    @Transactional
    public void deleteUser(final User user) {
        userRepo.delete(user);
    }

    @Override
    @Transactional
    public void changeUserPassword(final String username, final String password) {
        final User user = userRepo.findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void resetUserPassword(final User user) {
        user.setPassword(passwordEncoder.encode(user.getUsername()));
        userRepo.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        return userRepo.findByUsername(username);
    }

    private List<String> findAllUsersOnProject(final Integer projectId) {
        return userRepo.findAllUsersOnProject(projectId);
    }

    private boolean isCreator(final String currentLoggedInUser, final Integer projectId) {
        return projectService.findById(projectId).getCreator().equals(currentLoggedInUser);
    }

    private boolean isProjectManager(final String currentLoggedInUser, final Integer projectId) {
        return projectService.findById(projectId).getProjectManager().equals(currentLoggedInUser);
    }
}
