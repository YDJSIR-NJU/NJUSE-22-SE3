package com.assignment.collect.serviceImpl.task;

import com.assignment.collect.dao.task.TaskMapper;
import com.assignment.collect.dao.taskDescriptor.TaskDescriptorMapper;
import com.assignment.collect.po.task.Task;
import com.assignment.collect.serviceimpl.task.TaskServiceImpl;
import com.assignment.collect.util.Constant;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.task.TaskVo;
import com.github.pagehelper.PageInfo;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @Author: XiaYu
 * @Date 2022/3/1 14:42
 */
@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTests {

    private static final String filePath;
    private static final String tempDirectory;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("Files");
        if (File.separator.equals("/")) {
            filePath = bundle.getString("LINUX_FILEPATH");
            tempDirectory = bundle.getString("LINUX_TEMP");
        } else {
            filePath = bundle.getString("WINDOWS_FILEPATH");
            tempDirectory = bundle.getString("WINDOWS_TEMP");
        }
    }

    @Mock
    TaskMapper taskMapper;
    @Mock
    Task task;
    @Mock
    TaskDescriptorMapper taskDescriptorMapper;
    @InjectMocks
    TaskServiceImpl taskService;
    TaskVo taskVo;

    @BeforeEach
    public void set() {
        task.setId(Long.valueOf(1));
        task.setTaskDiscribe("hahah");
        taskVo = new TaskVo();
        taskVo.setCurrentNum(0);
        taskVo.setFinishTime(new Date());
        taskVo.setStartTime(new Date());
        taskVo.setTaskDiscribe("hello, this is my test");
        taskVo.setTotalNum(10);
        taskVo.setType("FUNCTION");
        taskVo.setUserId(1L);
        taskVo.setId(1L);
        taskVo.setTaskDiscribe("haah");
    }

    @Test
    public void releaseTaskTest() throws IOException {
        byte[] data1 = new byte[(int) (Math.random() * 1024 * 1024)];
        byte[] data2 = new byte[(int) (Math.random() * 1024 * 1024)];
        byte[] data3 = new byte[(int) (Math.random() * 1024 * 1024)];
        for (int i = 0; i < data1.length; i++) {
            data1[i] = (byte) i;
        }

        for (int i = 0; i < data2.length; i++) {
            data2[i] = (byte) i;
        }

        for (int i = 0; i < data3.length; i++) {
            data3[i] = (byte) i;
        }

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile(
                "file1.pdf",
                "file1.pdf",
                ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
                new ByteArrayInputStream(data1));

        MockMultipartFile mockMultipartFile2 = new MockMultipartFile(
                "file2.pdf",
                "file2.pdf",
                ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
                new ByteArrayInputStream(data2));

        MockMultipartFile mockMultipartFile3 = new MockMultipartFile(
                "file3.exe",
                "file3.exe",
                ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
                new ByteArrayInputStream(data3));

        MultipartFile[] files = {mockMultipartFile1, mockMultipartFile2, mockMultipartFile3};
        when(taskMapper.insert(any(Task.class))).thenReturn(1);
        when(taskDescriptorMapper.insert(any())).thenReturn(1);
        ResultVo<TaskVo> resultVo = taskService.releaseTask(taskVo, files);
        Assertions.assertEquals(Constant.REQUEST_SUCCESS, resultVo.getCode());
    }

    @Test
    public void getTaskListTest() {
        int currPage = 1;
        int pageSize = 10;
        int uid = 1;

        List<Task> taskList = new ArrayList<>();
        createTaskList((int) (Math.random() * 50), taskList);
        when(taskMapper.todoList()).thenReturn(taskList);
        PageInfo<TaskVo> pageInfo = taskService.getTaskList(currPage, pageSize, uid);
        Assertions.assertEquals(taskList.size(), pageInfo.getList().size());
    }

    @Test
    public void getTasksByUidTest() {
        int currPage = 1;
        int pageSize = 10;
        int uid = 1;

        List<Task> taskList = new ArrayList<>();
        createTaskList((int) (Math.random() * 50), taskList);
        when(taskMapper.todoList()).thenReturn(taskList);
        PageInfo<TaskVo> pageInfo = taskService.getTaskList(currPage, pageSize, uid);
        Assertions.assertEquals(taskList.size(), pageInfo.getList().size());
    }


    private void createTaskList(int nums, List<Task> list) {
        for (int i = 0; i < nums; i++) {
            list.add(new Task());
        }
    }


}
