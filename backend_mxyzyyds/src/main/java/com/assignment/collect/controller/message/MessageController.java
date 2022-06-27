package com.assignment.collect.controller.message;

import com.assignment.collect.service.message.MessageService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.JWTUtils;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.message.MessageVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:46
 */
@RestController
@RequestMapping("/message")
@Api(tags = "MessageController")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation("get")
    @GetMapping("/get/{uid}")
    public PageInfo<MessageVo> getMessageByUserId(@PathVariable Integer uid, @RequestParam Integer page, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return messageService.getMessageByUserId(page, Constant.PAGE_SIZE, uid);
    }

    @ApiOperation("get")
    @GetMapping("/get/new/{uid}")
    public PageInfo<MessageVo> getNewMessageByUserId(@PathVariable Integer uid, @RequestParam Integer page, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Integer.parseInt(subject.split("_")[0]);

        return messageService.getNewMessageByUserId(page, Constant.PAGE_SIZE, uid);
    }

    @ApiOperation("setRead")
    @PostMapping("/read")
    public ResultVo<MessageVo> setRead(@RequestParam Long messageid, @RequestParam Long uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Long.parseLong(subject.split("_")[0]);

        assert messageid != null;
        return messageService.setRead(messageid, uid);
    }

    @ApiOperation("write")
    @PostMapping("/write")
    public ResultVo<MessageVo> write(@RequestBody MessageVo messageVo, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        if (token == null) {
            return null;
        }
        return messageService.write(messageVo);
    }
}
