package pl.kozhanov.projectmanagementsystem.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRoleRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ProjectRoleServiceTest {

    ProjectRoleRepo projectRoleRepo = mock(ProjectRoleRepo.class);

    ProjectRoleService projectRoleService = new ProjectRoleService(projectRoleRepo);

    @Test
    void findAllRoleNamesTest() {

        List<ProjectRole> prl = new ArrayList<>();
        ProjectRole pr = new ProjectRole();
        pr.setRoleName("Test role");
        prl.add(pr);
        Mockito.when(projectRoleRepo.findAll()).thenReturn(prl);
        projectRoleService.findAllRoleNames();
        assertEquals("Test role", projectRoleService.findAllRoleNames().get(0));
    }

}