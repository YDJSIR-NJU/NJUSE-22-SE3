package com.assignment.collect.vo.message;

import com.assignment.collect.po.message.Message;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 13:49
 */
@Data
public class MessageVo {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    private String status;

    private String content;

    public MessageVo() {

    }

    public MessageVo(Message message) {
        this.id = message.getId();
        this.userId = message.getUserId();
        this.time = message.getTime();
        this.status = message.getStatus();
        this.content = message.getContent();
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
