package org.jeecg.modules.devops.compile.entity;

public class JenkinsStatus {
    private String id;
    private String status;

    public JenkinsStatus(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
