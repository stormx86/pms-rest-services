package pl.kozhanov.projectmanagementsystem.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "project_status")
public class ProjectStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String statusName;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private Set<Project> projects;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
