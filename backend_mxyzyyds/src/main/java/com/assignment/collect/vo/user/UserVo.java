package com.assignment.collect.vo.user;

import com.assignment.collect.enums.UserRole;
import com.assignment.collect.po.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: XiaYu
 * @Date 2022/2/18 21:43
 */
@Data
public class UserVo {
    private Long id;

    private String uname;

    private String password;

    private String email;

    private UserRole userRole;

    private Integer credit;

    private Integer level;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String preferTypes;


    public UserVo() {

    }

    public UserVo(User user) {
        this.id = user.getId();
        this.uname = user.getUname();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.userRole = user.getUserRole();
        this.createTime = user.getCreateTime();
        this.preferTypes = user.getPreferTypes();
        this.level = user.getLevel();
        this.credit = user.getCredit();
        this.preferTypes = user.getPreferTypes();
    }

}
