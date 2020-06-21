package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.Project;
import pl.kozhanov.ProjectManagementSystem.domain.User;
import pl.kozhanov.ProjectManagementSystem.repos.UserRepo;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

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

}
