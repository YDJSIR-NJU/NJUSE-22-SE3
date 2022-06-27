package com.assignment.collect.controller.user;

import com.assignment.collect.dto.UserRegistryDTO.UserRegistryDTO;
import com.assignment.collect.service.user.UserService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.user.UserVo;
import com.assignment.collect.vo.userDevice.UserDeviceVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: XiaYu
 * @Date 2022/2/19 13:11
 */
@RestController
@RequestMapping("/user")
@Api(tags = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("register")
    @PostMapping("/register")
    public ResultVo<UserVo> register(@RequestBody UserRegistryDTO userRegistryDTO) {
        return userService.userRegister(userRegistryDTO);
    }

    @ApiOperation("login")
    @PostMapping("/login")
    public ResultVo<UserVo> login(@RequestBody UserVo userVo, HttpServletResponse response) throws Exception {
        ResultVo<UserVo> resultVo = userService.userLogin(userVo.getEmail(), userVo.getPassword());
        if (resultVo.getCode().equals(Constant.REQUEST_SUCCESS)) {    //输入了正确的账号和密码，需要返回token
            response.setHeader(JWTUtils.header, JWTUtils.getToken(resultVo.getData().getId() + "_" + resultVo.getData().getUserRole()));
        }
        return resultVo;
    }

    @ApiOperation("getUser")
    @GetMapping("/get/{uid}")
    public ResultVo<UserVo> getUser(@PathVariable Integer uid, HttpServletRequest httpServletRequest) {
        // 这里很可能是查其他用户的信息，如credit和level，所以这接口不要替换uid
//        String token = httpServletRequest.getHeader(JWTUtils.header);
//        String subject = JWTUtils.verify(token);
//        uid = Integer.parseInt(subject.split("_")[0]);

        return userService.getUserVo(uid);
    }

    @ApiOperation("getUserDevices")
    @GetMapping("/devices/get/{uid}")
    public PageInfo<UserDeviceVo> getUserDevices(@PathVariable Integer uid, @RequestParam Integer page, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return userService.getUserDevices(page, Constant.PAGE_SIZE, uid);
    }

    @ApiOperation("addUserDevices")
    @PostMapping("/devices/add")
    public ResultVo<UserDeviceVo> addUserDevices(@RequestBody UserDeviceVo userDeviceVo, @RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return userService.addUserDevices(userDeviceVo, uid);
    }

    @ApiOperation("deleteUserDevices")
    @PostMapping("/devices/delete")
    public ResultVo<UserDeviceVo> deleteUserDevices(@RequestParam Long userDeviceRecordId, @RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return userService.deleteUserDevices(userDeviceRecordId, uid);
    }

    @ApiOperation("updateUserDevices")
    @PostMapping("/devices/update")
    public ResultVo<UserDeviceVo> updateUserDevices(@RequestBody UserDeviceVo userDeviceVo, @RequestParam Integer uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return userService.updateUserDevices(userDeviceVo, uid);
    }
}
