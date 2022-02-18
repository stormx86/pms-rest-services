package pl.kozhanov.projectmanagementsystem.service;

import pl.kozhanov.projectmanagementsystem.dto.NewProjectStatusRequestDto;
import pl.kozhanov.projectmanagementsystem.dto.ProjectStatusDto;

import java.util.List;

public interface ProjectStatusService {

    List<ProjectStatusDto> findAllStatuses();

    String setNewProjectStatus(NewProjectStatusRequestDto newProjectStatusRequestDto);
}
