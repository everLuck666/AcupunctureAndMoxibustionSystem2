package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`medical_record`")
public class MedicalRecord implements Serializable {
    @Column(name = "`user_id`")
    private String userId;

    @Column(name = "`project_id`")
    private String projectId;

    @Column(name = "`total_time`")
    private String totalTime;

    @Column(name = "`progress`")
    private String progress;

    private static final long serialVersionUID = 1L;

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return project_id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * @param projectId
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * @return total_time
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * @param totalTime
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * @return progress
     */
    public String getProgress() {
        return progress;
    }

    /**
     * @param progress
     */
    public void setProgress(String progress) {
        this.progress = progress;
    }
}