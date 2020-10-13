package pl.kozhanov.projectmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kozhanov.projectmanagementsystem.domain.ProjectStatus;
import pl.kozhanov.projectmanagementsystem.repos.ProjectStatusRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectStatusService {
    private ProjectStatusRepo projectStatusRepo;

    @Autowired
    public ProjectStatusService(ProjectStatusRepo projectStatusRepo) {
        this.projectStatusRepo = projectStatusRepo;
    }

    ProjectStatus findByStatusName(String statusName) {
        return projectStatusRepo.findByStatusName(statusName);
    }

    public List<String> findAllStatuses() {
        List<String> statuses = new ArrayList<>();
        projectStatusRepo.findAll().forEach(status -> statuses.add(status.getStatusName()));
        return statuses;
    }
}
