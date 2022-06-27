package com.assignment.collect.po.activeness;

import java.io.Serializable;
import java.util.Date;

public class Activeness implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date lastbugtime;
    private Long uid;
    private Date lastreporttime;
    private Date lasttasktime;
    private Long numbugsWeek;
    private Long numbugsMonth;
    private Long numreportsWeek;
    private Long numreportsMonth;
    private Long numtasksWeek;
    private Long numtasksMonth;

    public Activeness() {
        this.numbugsMonth = this.numbugsWeek = this.numreportsWeek = this.numreportsMonth = this.numtasksWeek = this.numtasksMonth = Long.valueOf(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastbugtime() {
        return lastbugtime;
    }

    public void setLastbugtime(Date lastbugtime) {
        this.lastbugtime = lastbugtime;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getLastreporttime() {
        return lastreporttime;
    }

    public void setLastreporttime(Date lastreporttime) {
        this.lastreporttime = lastreporttime;
    }

    public Date getLasttasktime() {
        return lasttasktime;
    }

    public void setLasttasktime(Date lasttasktime) {
        this.lasttasktime = lasttasktime;
    }

    public Long getNumbugsWeek() {
        return numbugsWeek;
    }

    public void setNumbugsWeek(Long numbugsWeek) {
        this.numbugsWeek = numbugsWeek;
    }

    public Long getNumbugsMonth() {
        return numbugsMonth;
    }

    public void setNumbugsMonth(Long numbugsMonth) {
        this.numbugsMonth = numbugsMonth;
    }

    public Long getNumreportsWeek() {
        return numreportsWeek;
    }

    public void setNumreportsWeek(Long numreportsWeek) {
        this.numreportsWeek = numreportsWeek;
    }

    public Long getNumreportsMonth() {
        return numreportsMonth;
    }

    public void setNumreportsMonth(Long numreportsMonth) {
        this.numreportsMonth = numreportsMonth;
    }

    public Long getNumtasksWeek() {
        return numtasksWeek;
    }

    public void setNumtasksWeek(Long numtasksWeek) {
        this.numtasksWeek = numtasksWeek;
    }

    public Long getNumtasksMonth() {
        return numtasksMonth;
    }

    public void setNumtasksMonth(Long numtasksMonth) {
        this.numtasksMonth = numtasksMonth;
    }
}