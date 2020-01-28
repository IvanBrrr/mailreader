package com.company.mailreader.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "MAILREADER_TASK")
@Entity(name = "mailreader_Task")
public class Task extends StandardEntity {
    private static final long serialVersionUID = -2603496685556278826L;

    @Column(name = "SHORTDESC")
    protected String shortdesc;

    @Column(name = "TESTING_PLAN")
    protected String testingPlan;

    @Column(name = "PLANNING_TIME")
    protected Double planningTime;

    @Column(name = "ACTUAL_TIME")
    protected Double actualTime;

    public Double getActualTime() {
        return actualTime;
    }

    public void setActualTime(Double actualTime) {
        this.actualTime = actualTime;
    }

    public Double getPlanningTime() {
        return planningTime;
    }

    public void setPlanningTime(Double planningTime) {
        this.planningTime = planningTime;
    }

    public String getTestingPlan() {
        return testingPlan;
    }

    public void setTestingPlan(String testingPlan) {
        this.testingPlan = testingPlan;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }
}