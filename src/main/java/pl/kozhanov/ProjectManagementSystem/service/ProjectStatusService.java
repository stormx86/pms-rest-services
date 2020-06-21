package pl.kozhanov.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.ProjectManagementSystem.domain.ProjectStatus;
import pl.kozhanov.ProjectManagementSystem.repos.ProjectStatusRepo;

@Service
public class ProjectStatusService {
    @Autowired
    ProjectStatusRepo projectStatusRepo;

    ProjectStatus findByStatusName(String statusName)
    {
        return projectStatusRepo.findByStatusName(statusName);
    }

}
