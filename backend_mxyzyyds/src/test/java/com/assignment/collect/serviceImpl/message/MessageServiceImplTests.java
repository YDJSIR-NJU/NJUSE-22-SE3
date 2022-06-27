package com.assignment.collect.serviceImpl.message;

import com.assignment.collect.dao.message.MessageMapper;
import com.assignment.collect.po.message.Message;
import com.assignment.collect.serviceimpl.message.MessageServiceImpl;
import com.assignment.collect.util.Constant;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.message.MessageVo;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTests {

    @Mock
    MessageMapper messageMapper;

    @InjectMocks
    MessageServiceImpl messageService;

    Message message = new Message();
    Message newmessage = new Message();
    List<Message> list = new ArrayList<>();
    List<Message> newlist = new ArrayList<>();

    @BeforeEach
    public void set() {
        message.setContent("消息");
        message.setUserId(1L);
        list.add(message);
        newmessage.setUserId(1L);
        newmessage.setContent("新消息");
        newmessage.setStatus("NEW");
        newlist.add(newmessage);
    }

    @Test
    void getMessageByUserIdTest() {
        when(messageMapper.selectByUserId(1L)).thenReturn(list);
        PageInfo<MessageVo> pageInfo = messageService.getMessageByUserId(1, 2, 1);
        Assertions.assertEquals("消息", pageInfo.getList().get(0).getContent());
    }

    @Test
    void getNewMessageByUserIdTest() {
        when(messageMapper.selectNewByUserId(1L)).thenReturn(newlist);
        PageInfo<MessageVo> pageInfo = messageService.getNewMessageByUserId(1, 2, 1);
        Assertions.assertEquals("新消息", pageInfo.getList().get(0).getContent());
    }

    @Test
    void setReadTest() {
        when(messageMapper.selectByPrimaryKey(1L)).thenReturn(newmessage);
        when(messageMapper.updateByPrimaryKey(any())).thenReturn(1);
        ResultVo<MessageVo> pageInfo = messageService.setRead(1L, 1L);
        Assertions.assertEquals(Constant.REQUEST_SUCCESS, pageInfo.getCode());
    }
}
