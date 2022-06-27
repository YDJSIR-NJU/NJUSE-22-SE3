package com.assignment.collect.po.user;

import com.assignment.collect.enums.UserRole;
import com.assignment.collect.vo.user.UserVo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String uname;
    private String password;
    private String email;
    private UserRole userRole;
    private Date createTime;
    private Integer credit;
    private Integer level;
    private String preferTypes;

    public User() {

    }

    public User(UserVo userVo) {
        this.createTime = userVo.getCreateTime();
        this.userRole = userVo.getUserRole();
        this.email = userVo.getEmail();
        this.password = userVo.getPassword();
        this.uname = userVo.getUname();
        this.id = userVo.getId();
        this.credit = userVo.getCredit();
        this.level = userVo.getLevel();
        this.preferTypes = userVo.getPreferTypes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCredit() {
        return this.credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPreferTypes() {
        return preferTypes;
    }

    public void setPreferTypes(String preferTypes) {
        this.preferTypes = preferTypes;
    }

}
