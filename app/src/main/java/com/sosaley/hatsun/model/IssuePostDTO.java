package com.sosaley.hatsun.model;

import com.google.gson.annotations.SerializedName;

public class IssuePostDTO {

    @SerializedName("project_id")
    private int projectId;

    @SerializedName("subject")
    private String subject;

    @SerializedName("priority_id")
    private int priorityId;

    @SerializedName("description")
    private String description;

    /*@SerializedName("is_private")
    private int isPrivate;*/

    @SerializedName("estimated_hours")
    private int estimatedHours;

    public IssuePostDTO(int projectId, String subject, int priorityId, String description, int estimatedHours) {
        this.projectId = projectId;
        this.subject = subject;
        this.priorityId = priorityId;
        this.description = description;

        this.estimatedHours = estimatedHours;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }
}


