package com.assignment.collect.vo.activeness;

import com.assignment.collect.po.activeness.Activeness;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ActivenessVo {
    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastbugtime;

    private Long uid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastreporttime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lasttasktime;

    private Long numbugsWeek;

    private Long numbugsMonth;

    private Long numreportsWeek;

    private Long numreportsMonth;

    private Long numtasksWeek;

    private Long numtasksMonth;

    public ActivenessVo() {

    }

    public ActivenessVo(Activeness activeness) {
        this.id = activeness.getId();
        this.uid = activeness.getUid();
        this.lastbugtime = activeness.getLastbugtime();
        this.numbugsWeek = activeness.getNumbugsWeek();
        this.numreportsWeek = activeness.getNumreportsWeek();
        this.numtasksWeek = activeness.getNumtasksWeek();
        this.numtasksMonth = activeness.getNumtasksMonth();
        this.numbugsMonth = activeness.getNumbugsMonth();
        this.numreportsMonth = activeness.getNumreportsMonth();
        this.lastreporttime = activeness.getLastreporttime();
        this.lasttasktime = activeness.getLasttasktime();

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