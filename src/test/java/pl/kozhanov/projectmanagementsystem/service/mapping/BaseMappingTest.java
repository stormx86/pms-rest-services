package pl.kozhanov.projectmanagementsystem.service.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pl.kozhanov.projectmanagementsystem.repos.CommentRepo;
import pl.kozhanov.projectmanagementsystem.repos.ProjectRepo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

@ContextConfiguration
@ActiveProfiles(value = {"mappingTest"})
public abstract class BaseMappingTest extends AbstractTestNGSpringContextTests {

    @Autowired
    OrikaBeanMapper beanMapper;

    @Autowired
    ProjectRepo projectRepo;

    @Autowired
    CommentRepo commentRepo;

    @BeforeClass
    public void mainBeforeClass(){beforeClass();}

    @BeforeMethod
    public void mainBeforeMethod(){
        reset(projectRepo, commentRepo);
    }

    void beforeClass(){

    }

    void beforeMethod(){

    }

    @Configuration
    @Profile({"mappingTest"})
    @ComponentScan({"pl.kozhanov.projectmanagementsystem.service.mapping"})
    static class LocalConfig{

        @Bean
        public ProjectRepo projectRepo() {return mock(ProjectRepo.class);}

        @Bean
        public CommentRepo commentRepo() {return mock(CommentRepo.class);}
    }
}
