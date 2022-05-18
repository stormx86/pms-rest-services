package pl.kozhanov.projectmanagementsystem.domain;

public enum ProjectStatus {
    WAITING(0, "Waiting"),
    PROCESSING(1, "Processing"),
    CLOSED(2, "Closed");

    private int code;

    private String description;

    ProjectStatus(int code,
                  String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
