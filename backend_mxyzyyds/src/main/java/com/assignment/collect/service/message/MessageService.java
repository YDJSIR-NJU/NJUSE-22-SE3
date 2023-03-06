package com.assignment.collect.service.message;

import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.message.MessageVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

/**
 * @Author: XiaYu
 * @Date 2022/3/25 14:49
 */
@Service
public interface MessageService {
    PageInfo<MessageVo> getMessageByUserId(Integer currPage, Integer pageSize, Integer uid);

    PageInfo<MessageVo> getNewMessageByUserId(Integer currPage, Integer pageSize, Integer uid);

    ResultVo<MessageVo> setRead(Long messageid, Long uid);

    ResultVo<Integer> setAllRead(Long uid);

    ResultVo<MessageVo> write(MessageVo messageVo);
}
