package pl.kozhanov.projectmanagementsystem.dto;

public class ProjectStatusDto {

    private int code;

    private String statusName;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
