package com.assignment.collect.po.dupBug;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class DupBug implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer reportid;
    private Integer dupid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportid() {
        return reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public Integer getDupid() {
        return dupid;
    }

    public void setDupid(Integer dupid) {
        this.dupid = dupid;
    }
}