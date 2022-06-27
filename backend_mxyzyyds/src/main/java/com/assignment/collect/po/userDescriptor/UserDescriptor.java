package com.assignment.collect.po.userDescriptor;

import java.io.Serializable;

public class UserDescriptor implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer userid;
    private String words;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}