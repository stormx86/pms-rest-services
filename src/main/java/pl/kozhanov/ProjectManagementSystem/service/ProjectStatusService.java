package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.ProjectStatus;
import pl.kozhanov.ProjectManagementSystem.repos.ProjectStatusRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectStatusService {

    @Autowired
    ProjectStatusRepo projectStatusRepo;

    public ProjectStatusService(ProjectStatusRepo projectStatusRepo) {
        this.projectStatusRepo = projectStatusRepo;
    }

    public ProjectStatus findByStatusName(String statusName)
    {
        return projectStatusRepo.findByStatusName(statusName);
    }

    public  List<String> findAllStatuses(){
        List<String> statuses = new ArrayList<>();
        for(ProjectStatus pr:projectStatusRepo.findAll()){
            statuses.add(pr.getStatusName());
        }
        return statuses;
    }

}
