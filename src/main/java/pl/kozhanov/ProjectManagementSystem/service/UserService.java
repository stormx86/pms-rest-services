package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.GlobalRole;
import pl.kozhanov.ProjectManagementSystem.domain.User;
import pl.kozhanov.ProjectManagementSystem.repos.UserRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProjectService projectService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> findAll(){
       return userRepo.findAll();
    }

public User findByUsername(String username){ return  userRepo.findByUsername(username); }

/*public String[] findAllNames(){
    List<String> usernames = new ArrayList<>();
    for(User user: userRepo.findAll()){
        usernames.add(user.getUsername());
    }

    return usernames.toArray(new String[0]);
}*/

    //usernames for autocomplete
public List<String> findByUsernameLike(String term)
{
   return userRepo.findByUsernameLike(term);
}

public List<String> findAllUsersOnProject(Integer projectId) {return userRepo.findAllUsersOnProject(projectId);}

public String getCurrentLoggedInUsername()
{
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
        return ((UserDetails)principal).getUsername();
    } else {
        return principal.toString();
    }
}

public boolean isAdmin()
{
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
        return true;
    }
    else return false;
}

public boolean isCreator(String currentLoggedInUser, Integer projectId){
        if(projectService.findById(projectId).getCreator().equals(currentLoggedInUser))
        {
            return true;
        }
        else return false;
}

public boolean isProjectManager(String currentLoggedInUser, Integer projectId){
        if(projectService.findById(projectId).getProjectManager().equals(currentLoggedInUser))
        {
            return true;
        }
        else return false;
    }

public boolean hasProjectAuthorities(String currentLoggedInUser, Integer projectId){
    if(!findAllUsersOnProject(projectId).contains(currentLoggedInUser) &&
            !isAdmin() &&
            !isCreator(currentLoggedInUser, projectId) &&
            !isProjectManager(currentLoggedInUser, projectId)) return false;
    else return true;
}

public void addUser(User user){
        //as option add "email" field to User entity, enter username & email and send random(regular expression) password by email;
        user.setPassword(passwordEncoder.encode(user.getUsername()));
        user.setActive(true);
        user.setGlobalRoles(Collections.singleton(GlobalRole.USER));
        userRepo.save(user);
}

    public String saveUser(Integer userId, String newUsername, String[] roles){
        for(User u:userRepo.findAll())
        {
            //if newUsername is in userDB & newUsername != username of current userID
            if (u.getUsername().equals(newUsername) && !u.getUsername().equals(userRepo.getById(userId).getUsername())) return "Username already exists";

        }

        User user = userRepo.getById(userId);
        user.setUsername(newUsername);
        user.getGlobalRoles().clear();

        for(int i=0; i<roles.length; i++)
            {
                user.getGlobalRoles().add(GlobalRole.valueOf(roles[i]));
            }
        userRepo.save(user);
        return "Successfully saved!";
    }

    public void deleteUser(User user){
        userRepo.delete(user);
    }


    public void changeUserPassword(String username, String password){
        User user = userRepo.findByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
