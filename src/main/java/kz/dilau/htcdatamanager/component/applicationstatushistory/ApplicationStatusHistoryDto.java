package kz.dilau.htcdatamanager.component.applicationstatushistory;

public class ApplicationStatusHistoryDto {
    private Long applicationStatusId;
    private Long applicationId;
    private String comment;

    public Long getApplicationStatusId() {
        return applicationStatusId;
    }

    public void setApplicationStatusId(Long applicationStatusId) {
        this.applicationStatusId = applicationStatusId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
