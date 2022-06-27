package com.assignment.collect.po.message;

import com.assignment.collect.vo.message.MessageVo;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private Date time;
    private String status;
    private String content;

    public Message() {
        this.id = null;
        this.status = "NEW";
        this.time = new Date(System.currentTimeMillis());
    }

    public Message(MessageVo messageVO) {
        this.id = messageVO.getId();
        this.userId = messageVO.getUserId();
        this.time = messageVO.getTime();
        this.status = messageVO.getStatus();
        this.content = messageVO.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
