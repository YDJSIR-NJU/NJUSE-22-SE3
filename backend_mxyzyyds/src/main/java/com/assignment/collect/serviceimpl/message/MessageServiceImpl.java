package com.assignment.collect.serviceimpl.message;

import com.assignment.collect.dao.message.MessageMapper;
import com.assignment.collect.po.message.Message;
import com.assignment.collect.service.message.MessageService;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.PageInfoUtil;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.message.MessageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:52
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public PageInfo<MessageVo> getMessageByUserId(Integer currPage, Integer pageSize, Integer uid) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<Message> po = new PageInfo<>(messageMapper.selectByUserId((long) uid));
        System.out.println(po);
        return getMessageVo(uid, po);
    }

    @Override
    public PageInfo<MessageVo> getNewMessageByUserId(Integer currPage, Integer pageSize, Integer uid) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<Message> po = new PageInfo<>(messageMapper.selectNewByUserId((long) uid));
        System.out.println(po);
        return getMessageVo(uid, po);
    }

    @Override
    public ResultVo<MessageVo> setRead(Long messageid, Long uid) {
        Message message = messageMapper.selectByPrimaryKey(messageid);
        message.setStatus("READ");
        messageMapper.updateByPrimaryKey(message);
        return new ResultVo<MessageVo>(Constant.REQUEST_SUCCESS, "设置已读成功", new MessageVo(message));
    }

    @Override
    public ResultVo<MessageVo> write(MessageVo messageVo) {
        Message message = new Message(messageVo);
        message.setTime(new Date());
        message.setStatus("NEW");
        if (messageMapper.insert(message) > 0)
            return new ResultVo<MessageVo>(Constant.REQUEST_SUCCESS, "发送成功", new MessageVo(message));
        return new ResultVo<>(Constant.REQUEST_FAIL, "发送失败");
    }

    PageInfo<MessageVo> getMessageVo(Integer uid, PageInfo<Message> po) {
        return PageInfoUtil.convert(po, MessageVo.class);
    }

}
