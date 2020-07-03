package pl.kozhanov.ProjectManagementSystem.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface ProjectMainProjection {

    Integer getId();
    Instant getCreatedAt();
    String getTitle();
    String getStatus();
    String getPmName();
    String getCreatorName();


    default String getCreatedAtFormatted(){
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(ZonedDateTime.ofInstant(getCreatedAt(), ZoneId.systemDefault()));
    }
}
