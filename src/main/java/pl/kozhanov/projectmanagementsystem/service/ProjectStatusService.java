package pl.kozhanov.projectmanagementsystem.service;

import pl.kozhanov.projectmanagementsystem.domain.ProjectStatus;

import java.util.List;

public interface ProjectStatusService {

    ProjectStatus findByStatusName(String statusName);

    List<String> findAllStatuses();
}
