package com.assignment.collect.serviceimpl.user;

import com.assignment.collect.dao.message.MessageMapper;
import com.assignment.collect.dao.user.UserMapper;
import com.assignment.collect.dao.userDevice.UserDeviceMapper;
import com.assignment.collect.dto.UserRegistryDTO.UserRegistryDTO;
import com.assignment.collect.po.message.Message;
import com.assignment.collect.po.user.User;
import com.assignment.collect.po.userDevice.UserDevice;
import com.assignment.collect.service.user.UserService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.PageInfoUtil;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.user.UserVo;
import com.assignment.collect.vo.userDevice.UserDeviceVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Author: XiaYu
 * @Date 2022/2/19 13:56
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDeviceMapper userDeviceMapper;

    @Autowired
    MessageMapper messageMapper;


    @Override
    public ResultVo<UserVo> userRegister(UserRegistryDTO userRegistryDTO) {
        UserDeviceVo[] userDeviceVos = userRegistryDTO.userDeviceVos;
        UserVo userVo = userRegistryDTO.userVo;

        String uname = userVo.getUname();
        String password = userVo.getPassword();
        String email = userVo.getEmail();
        if (userMapper.selectByEmail(email) == null) {       //该邮箱没有注册过账户
            if (StringUtils.hasLength(uname) && StringUtils.hasLength(password)) {
                userVo.setCreateTime(new Date());
                userVo.setCredit(0);
                userVo.setLevel(0);
                String md5 = DigestUtils.md5DigestAsHex(email.concat(password).getBytes());
                userVo.setPassword(md5);
                User user = new User(userVo);
                userMapper.insert(user);
                Long newUserId = user.getId();
                for (UserDeviceVo userDeviceVo : userDeviceVos
                ) {
                    UserDevice userDevice = new UserDevice(userDeviceVo);
                    userDevice.setUserId(newUserId); // 根据新用户的ID来
                    userDeviceMapper.insert(userDevice);
                }
                user.setPassword(null);
                Message message = new Message();
                message.setUserId(user.getId());
                message.setContent(user.getUname() + "，欢迎来到COLLECT！");
                messageMapper.insert(message);
                return new ResultVo<>(Constant.REQUEST_SUCCESS, "注册成功", new UserVo(user));
            } else {
                return new ResultVo<>(Constant.REQUEST_FAIL, "用户名密码不能为空");
            }
        }
        return new ResultVo<>(Constant.REQUEST_FAIL, "该邮箱已注册过账号");
    }

    @Override
    public ResultVo<UserVo> userLogin(String email, String password) {
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            return new ResultVo<>(Constant.REQUEST_FAIL, "该邮箱尚未注册账号");
        } else {
            String md5 = DigestUtils.md5DigestAsHex(email.concat(password).getBytes());
            if (!user.getPassword().equals(md5)) {
                return new ResultVo<>(Constant.REQUEST_FAIL, "账号或密码错误");
            }
        }
        user.setPassword(null);
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "登陆成功", new UserVo(user));
    }

    @Override
    public ResultVo<UserVo> getUserVo(Integer uid) {
        try {
            UserVo userVo = new UserVo(userMapper.selectByPrimaryKey(uid.longValue()));
            userVo.setPassword(null); // 不能透露密码
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "获取用户成功", userVo);
        } catch (Exception e) {
            return new ResultVo<>(Constant.REQUEST_FAIL, "获取用户失败", null);
        }
    }

    @Override
    public ResultVo<UserDeviceVo> addUserDevices(UserDeviceVo userDeviceVo, Integer uid) {
        userDeviceVo.setId(null);
        UserDevice userDevice = new UserDevice(userDeviceVo);
        int res = userDeviceMapper.insert(userDevice);
        System.out.println(userDevice);
        if (res == 1) {
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "增加用户设备成功", new UserDeviceVo(userDevice));
        } else {
            return new ResultVo<>(Constant.REQUEST_FAIL, "增加用户设备失败", null);
        }
    }

    @Override
    public ResultVo<UserDeviceVo> deleteUserDevices(Long userDeviceRecordId, Integer uid) {
        int res = userDeviceMapper.deleteByPrimaryKey(userDeviceRecordId);
        if (res == 1) {
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "删除用户设备成功", null);
        } else {
            return new ResultVo<>(Constant.REQUEST_FAIL, "删除用户设备失败", null);
        }
    }

    @Override
    public ResultVo<UserDeviceVo> updateUserDevices(UserDeviceVo userDeviceVo, Integer uid) {
        int res = userDeviceMapper.updateByPrimaryKey(new UserDevice(userDeviceVo));
        if (res == 1) {
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "修改用户设备成功", userDeviceVo);
        } else {
            return new ResultVo<>(Constant.REQUEST_FAIL, "修改用户设备失败", null);
        }
    }

    @Override
    public PageInfo<UserDeviceVo> getUserDevices(Integer currPage, Integer pageSize, Integer uid) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<UserDevice> po = new PageInfo<>(userDeviceMapper.selectByUserId(uid));
        System.out.println(po);
        return getMessageVo(uid, po);
    }

    PageInfo<UserDeviceVo> getMessageVo(Integer uid, PageInfo<UserDevice> po) {
        return PageInfoUtil.convert(po, UserDeviceVo.class);
    }
}
