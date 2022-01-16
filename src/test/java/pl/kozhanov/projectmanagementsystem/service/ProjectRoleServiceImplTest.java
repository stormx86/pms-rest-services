package pl.kozhanov.projectmanagementsystem.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.kozhanov.projectmanagementsystem.domain.ProjectRole;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRoleRepo;
import pl.kozhanov.projectmanagementsystem.service.impl.ProjectRoleServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectRoleServiceImplTest {
    private ProjectRoleRepo projectRoleRepo = mock(ProjectRoleRepo.class);

    private ProjectRoleServiceImpl projectRoleService = new ProjectRoleServiceImpl(projectRoleRepo);

    @Test
    void findAllRoleNamesTest() {

        List<ProjectRole> prl = new ArrayList<>();
        ProjectRole pr = new ProjectRole();
        pr.setRoleName("Test role");
        prl.add(pr);
        when(projectRoleRepo.findAll()).thenReturn(prl);
        projectRoleService.findAllRoleNames();
        assertEquals("Test role", projectRoleService.findAllRoleNames().get(0));
    }
}