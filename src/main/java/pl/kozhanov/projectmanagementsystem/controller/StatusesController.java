package pl.kozhanov.projectmanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kozhanov.projectmanagementsystem.dto.NewProjectStatusRequestDto;
import pl.kozhanov.projectmanagementsystem.dto.ProjectStatusDto;
import pl.kozhanov.projectmanagementsystem.service.ProjectStatusService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/status")
public class StatusesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusesController.class);

    private final ProjectStatusService projectStatusService;

    public StatusesController(final ProjectStatusService projectStatusService) {
        this.projectStatusService = projectStatusService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProjectStatusDto>> getAllProjectStatuses() {
        LOGGER.info("Retrieving all existing project statuses.");
        return  ResponseEntity.ok(projectStatusService.findAllStatuses());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> setNewProjectStatus(@RequestBody final NewProjectStatusRequestDto newProjectStatusRequestDto) {
        LOGGER.info("Setting new project status for project id :" + newProjectStatusRequestDto.getProjectId());

        return  ResponseEntity.ok(projectStatusService.setNewProjectStatus(newProjectStatusRequestDto));
    }
}