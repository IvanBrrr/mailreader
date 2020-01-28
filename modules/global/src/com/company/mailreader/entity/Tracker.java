package com.company.mailreader.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@NamePattern("%s|description")
@Table(name = "MAILREADER_TRACKER")
@Entity(name = "mailreader_Tracker")
public class Tracker extends StandardEntity {
    private static final long serialVersionUID = 578330509317076622L;

    @Column(name = "STEP_NAME")
    protected String stepName;

    @Column(name = "SHORT_DESC")
    protected String shortDesc;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    protected Task project;

    @Column(name = "TYPE_")
    protected String type;

    @Column(name = "TRACKER_PRIORITY_TYPE")
    protected String trackerPriorityType;

    public String getTrackerPriorityType() {
        return trackerPriorityType;
    }

    public void setTrackerPriorityType(String trackerPriorityType) {
        this.trackerPriorityType = trackerPriorityType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Task getProject() {
        return project;
    }

    public void setProject(Task project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
}