package pl.kozhanov.projectmanagementsystem.domain;

public enum ProjectStatus {
    WAITING(0),
    PROCESSING(1),
    CLOSED(2);

    private int code;

    ProjectStatus(int code) {this.code = code;}

    public int getCode() {
        return code;
    }
}
