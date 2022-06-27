package com.assignment.collect.serviceImpl.user;

import com.assignment.collect.dao.message.MessageMapper;
import com.assignment.collect.dao.user.UserMapper;
import com.assignment.collect.dao.userDevice.UserDeviceMapper;
import com.assignment.collect.dto.UserRegistryDTO.UserRegistryDTO;
import com.assignment.collect.enums.UserRole;
import com.assignment.collect.po.user.User;
import com.assignment.collect.po.userDevice.UserDevice;
import com.assignment.collect.serviceimpl.user.UserServiceImpl;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.user.UserVo;
import com.assignment.collect.vo.userDevice.UserDeviceVo;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @Author: XiaYu
 * @Date 2022/3/1 21:53
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    User user;
    UserVo userVo;
    UserDevice userDevice;
    List<UserDevice> lists = new ArrayList<>();
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserDeviceMapper userDiviceMapper;
    @Mock
    private MessageMapper messageMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void set() {
        user = new User();
        user.setUname("Rain");
        user.setPassword("123456");
        user.setEmail("123456@qq.com");
        user.setUserRole(UserRole.WORKER);
        userVo = new UserVo(user);
        userDevice = new UserDevice();
        userDevice.setId(1L);
        userDevice.setUserId(1L);
        userDevice.setDeviceBrand("DELL");
        userDevice.setOperatingSystem("LINUX");
        lists.add(userDevice);
    }

    @Test
    public void registerTest() {
        when(userMapper.selectByEmail("123456@qq.com")).thenReturn(null);
        UserDeviceVo[] userDeviceVos = new UserDeviceVo[1];
        userDeviceVos[0] = new UserDeviceVo(userDevice);
        UserRegistryDTO userRegistryDTO = new UserRegistryDTO(userDeviceVos, userVo);
        ResultVo<UserVo> resultVo = userService.userRegister(userRegistryDTO);
        Assertions.assertEquals("Rain", resultVo.getData().getUname());
    }


    @Test
    public void loginTest() {
        String email = "123456@qq.com";
        String password = "123456";
        user.setPassword(DigestUtils.md5DigestAsHex(email.concat(password).getBytes()));
        when(userMapper.selectByEmail(email)).thenReturn(user);
        ResultVo<UserVo> resultVo = userService.userLogin(email, password);
        Assertions.assertEquals("Rain", resultVo.getData().getUname());
    }

    @Test
    public void addUserDevicesTest() {
        when(userDiviceMapper.insert(any(UserDevice.class))).thenReturn(1);
        ResultVo resultVo = userService.addUserDevices(new UserDeviceVo(), 1);
        Assertions.assertEquals("增加用户设备成功", resultVo.getMsg());

    }

    @Test
    public void deleteUserDevicesTest() {
        when(userDiviceMapper.deleteByPrimaryKey(1L)).thenReturn(1);
        ResultVo resultVo = userService.deleteUserDevices(1L, 1);
        Assertions.assertEquals("删除用户设备成功", resultVo.getMsg());

    }

    @Test
    public void updateUserDevicesTest() {
        when(userDiviceMapper.updateByPrimaryKey(any(UserDevice.class))).thenReturn(1);
        ResultVo resultVo = userService.updateUserDevices(new UserDeviceVo(userDevice), 1);
        Assertions.assertEquals("修改用户设备成功", resultVo.getMsg());

    }

    @Test
    public void getUserDevicesTest() {
        when(userDiviceMapper.selectByUserId(1)).thenReturn(lists);
        PageInfo<UserDeviceVo> resultVo = userService.getUserDevices(1, 2, 1);
        Assertions.assertEquals(1, resultVo.getList().get(0).getId());
    }

}
