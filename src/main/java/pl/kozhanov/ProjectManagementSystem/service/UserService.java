package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.Project;
import pl.kozhanov.ProjectManagementSystem.domain.User;
import pl.kozhanov.ProjectManagementSystem.repos.UserRepo;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;


public User findByUsername(String username){ return  userRepo.findByUsername(username); }

public String[] findAllNames(){
    List<String> usernames = new ArrayList<>();
    for(User user: userRepo.findAll()){
        usernames.add(user.getUsername());
    }

    return usernames.toArray(new String[0]);
}

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

public boolean ifAdmin()
{
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
        return true;
    }
    else return false;
}


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
