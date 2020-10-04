package pl.kozhanov.projectmanagementsystem.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.kozhanov.projectmanagementsystem.domain.ProjectStatus;
import pl.kozhanov.projectmanagementsystem.repos.ProjectStatusRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ProjectStatusServiceTest {

    ProjectStatusRepo projectStatusRepo = mock(ProjectStatusRepo.class);

    ProjectStatusService projectStatusService = new ProjectStatusService(projectStatusRepo);

    @Test
    void findAllStatusesTest() {
        List<ProjectStatus> lps = new ArrayList<>();
        ProjectStatus ps = new ProjectStatus();
        ps.setStatusName("New");
        lps.add(ps);
        Mockito.when(projectStatusRepo.findAll()).thenReturn(lps);
        projectStatusService.findAllStatuses();
        assertNotNull(projectStatusService.findAllStatuses());
        assertEquals("New", projectStatusService.findAllStatuses().get(0));

    }


}