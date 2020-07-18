package pl.kozhanov.ProjectManagementSystem.domain;

import org.springframework.security.core.GrantedAuthority;

public enum GlobalRole implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() { return name(); }
}
