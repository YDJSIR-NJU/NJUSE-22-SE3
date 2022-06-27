package com.assignment.collect.serviceimpl.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.assignment.collect.dao.activeness.ActivenessMapper;
import com.assignment.collect.dao.task.TaskMapper;
import com.assignment.collect.dao.taskDescriptor.TaskDescriptorMapper;
import com.assignment.collect.dao.testRecord.TestRecordMapper;
import com.assignment.collect.dao.userCapability.UserCapabilityMapper;
import com.assignment.collect.dao.userDescriptor.UserDescriptorMapper;
import com.assignment.collect.dataProcess.SimilarityMeasure;
import com.assignment.collect.dataProcess.WordSegment;
import com.assignment.collect.po.activeness.Activeness;
import com.assignment.collect.po.task.Task;
import com.assignment.collect.po.taskDescriptor.TaskDescriptor;
import com.assignment.collect.po.testRecord.TestRecord;
import com.assignment.collect.po.userDescriptor.UserDescriptor;
import com.assignment.collect.service.task.TaskService;
import com.assignment.collect.util.COSUtil;
import com.assignment.collect.util.Constant;
import com.assignment.collect.util.FileHelper;
import com.assignment.collect.util.PageInfoUtil;
import com.assignment.collect.vo.ResultVo;
import com.assignment.collect.vo.file.FileInfoVo;
import com.assignment.collect.vo.task.TaskVo;
import com.assignment.collect.vo.testRecord.TestRecordVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.assignment.collect.util.Constant.filePath;
import static com.assignment.collect.util.Constant.tempDirectory;

/**
 * @Author: XiaYu
 * @Date 2022/2/24 14:53
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TestRecordMapper testRecordMapper;
    @Autowired
    ActivenessMapper activenessMapper;
    @Autowired
    UserCapabilityMapper userCapabilityMapper;

    @Autowired
    TaskDescriptorMapper taskDescriptorMapper;

    @Autowired
    UserDescriptorMapper userDescriptorMapper;

    @Override
    public PageInfo<TaskVo> getTaskList(Integer currPage, int pageSize, int uid) {
        if (currPage == null || currPage < 1) currPage = 1;  //默认为第一页
        PageHelper.startPage(currPage, pageSize);
        PageInfo<Task> po = new PageInfo<>(taskMapper.todoList());
        return getTaskVOPageInfo(uid, po);
    }

    /**
     * @Author:MJW
     * @Date
     */

    @Override
    public ResultVo<TaskVo> releaseTask(TaskVo taskVo, MultipartFile[] files) {
        try {
            Task task = new Task(taskVo);
            String curTimeMillis = String.valueOf(System.currentTimeMillis());
            List<FileInfoVo> fileInfoVoList = FileHelper.save(filePath + curTimeMillis + '/', files);
            List<String> exeFiles = new ArrayList<>();
            List<String> desFiles = new ArrayList<>();
            for (FileInfoVo fileInfoVO : fileInfoVoList) {
                String localPath = curTimeMillis + '/' + fileInfoVO.getPath();
                if (fileInfoVO.getType().equals("pdf")) {     //说明文件
                    desFiles.add(localPath);
                    COSUtil.uploadObject(filePath + localPath, "/upload/" + localPath); // 如果是PDF，在网页上呈现第一个
                } else {  //可执行文件
                    exeFiles.add(localPath);
                }
            }
            task.setDiscribFilePath(JSON.toJSONString(desFiles));   // 文件路径序列化
            task.setExeFilePath(JSON.toJSONString(exeFiles));
            if (taskMapper.insert(task) > 0) {
                // todo
                //如果任务发布成功，则需要将其分词存放在数据库表中
//                assert task.getId() != null;
                String descriptor = task.getTaskDiscribe();
                List<String> strings = WordSegment.segmentWord(descriptor);
//                Set<String> words = new HashSet<>(strings); // 对于任务来说，只考虑是否出现，不考虑出现次数
//                HashMap<String, Integer> hashMap = new HashMap<>();
//                for (String word : words) {
//                    hashMap.put(word, 1);
//                }
                HashMap<String, Integer> hashMap = new HashMap<>();
                for (String word : strings) {
                    hashMap.put(word, hashMap.getOrDefault(word, 0) + 1);
                }
                TaskDescriptor taskDescriptor = new TaskDescriptor();
                taskDescriptor.setTaskid(task.getId().intValue());
                taskDescriptor.setWords(JSON.toJSONString(hashMap));
                taskDescriptorMapper.insert(taskDescriptor);
                return new ResultVo<>(Constant.REQUEST_SUCCESS, "任务发布成功", new TaskVo(task));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultVo<>(Constant.REQUEST_FAIL, "任务创建失败");
    }

    /**
     * @Author:MJW
     * @Date
     */
    @Override
    public PageInfo<TaskVo> getTasksByUid(Integer currPage, Integer pageSize, Integer uid) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        PageInfo<Task> po = new PageInfo<>(taskMapper.selectByUid((long) uid));
        return getTaskVOPageInfo(uid, po);
    }

    /**
     * @Author:MJW
     * @Date
     */
    @Override
    public PageInfo<TaskVo> getTasksProcessingByuid(Integer currPage, Integer pageSize, Integer uid) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        List<Task> tasksList = taskMapper.selectProcessingByUid((long) uid);//满足条件的task 的id列表
        PageInfo<Task> po = new PageInfo<>(tasksList);
        return getTaskVOPageInfo(uid, po);
    }

    /**
     * @Author:MJW
     * @Date
     */
    @Override
    public PageInfo<TaskVo> getTasksHistoryByuid(Integer currPage, Integer pageSize, Integer uid) {
        if (currPage == null || currPage < 1) currPage = 1;
        PageHelper.startPage(currPage, pageSize);
        List<Task> tasksList = taskMapper.selectHistoryByUid((long) uid);//满足条件的task 的id列表
        System.out.println(tasksList);
        PageInfo<Task> po = new PageInfo<>(tasksList);
        return getTaskVOPageInfo(uid, po);
    }

    /**
     * @Author:MJW
     * @Date
     */
    @Override
    public PageInfo<TaskVo> getAll(Integer currPage, Integer pageSize, Integer uid) {
        assert uid != null;
        if (currPage == null || currPage < 1) currPage = 1;  //默认为第一页
        PageHelper.startPage(currPage, pageSize);
        PageInfo<Task> po = new PageInfo<>(taskMapper.selectAll());
        return getTaskVOPageInfo(uid, po);
    }

    /**
     * @Author:MJW
     * @Date
     */
    @Override
    public ResultVo<TestRecordVo> accept(Integer taskid, Integer uid) {
        assert uid != null;
        List<TestRecord> records = testRecordMapper.selectByTaskIdAndUid((long) taskid, (long) uid);
        Task task = taskMapper.selectByPrimaryKey(Long.valueOf(taskid));
        if (task.getCurrentNum() >= task.getTotalNum()) { // 人满了
            return new ResultVo<>(Constant.REQUEST_FAIL, "该任务已满员", new TestRecordVo());
        }
        if (records.size() > 0) { // 一个用户只能接受一次任务
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "已接受该任务", new TestRecordVo(records.get(0)));
        }
        TestRecordVo testRecordVo = new TestRecordVo();
        testRecordVo.setCommitTime(new Date());
        testRecordVo.setTaskId((long) taskid);
        testRecordVo.setUserId((long) uid);
        testRecordVo.setFinished(false);
        testRecordVo.setFinishTime(new Date(0));
        try {
            TestRecord testRecord = new TestRecord(testRecordVo);
            if (testRecordMapper.insert(testRecord) > 0) {
                task.setCurrentNum(task.getCurrentNum() + 1); // 更新正在做的工人数量
                taskMapper.updateByPrimaryKey(task);
                Activeness activeness = activenessMapper.selectByUserID(uid.longValue());
                if (activeness == null) {
                    activeness = new Activeness();
                    activeness.setUid(uid.longValue());
                    activenessMapper.insert(activeness);
                    activeness = activenessMapper.selectByUserID(uid.longValue());
                }
                activeness.setLasttasktime(new Date());
                activenessMapper.updateByPrimaryKey(activeness);
                return new ResultVo<>(Constant.REQUEST_SUCCESS, "创建测试记录成功", new TestRecordVo(testRecord));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVo<>(Constant.REQUEST_FAIL, "创建测试记录失败");
        }
        return new ResultVo<>(Constant.REQUEST_FAIL, "创建测试记录失败");
    }

    @Override
    public ResultVo<TestRecordVo> check(Integer taskid, Integer uid) {
        List<TestRecord> records = testRecordMapper.selectByTaskIdAndUid((long) taskid, (long) uid);
        if (records.size() > 0) { // 一个用户只能接受一次任务
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "已接受该任务", new TestRecordVo(records.get(0)));
        } else {
            return new ResultVo<>(Constant.REQUEST_FAIL, "未接受该任务", new TestRecordVo());
        }
    }

    @Override
    public ResultVo<TaskVo> getDetail(Integer uid, Integer taskid) {
        assert taskid != null;
        Task task = taskMapper.selectByPrimaryKey(Long.valueOf(taskid));
        if (task == null)
            return new ResultVo<>(Constant.REQUEST_FAIL, "该任务不存在");
        else
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "该任务存在", new TaskVo(task));
    }

    @Override
    public void downloadDocs(Integer uid, Integer taskId, HttpServletResponse response) {
        assert taskId != null;
        if (taskId == null) {
            return;
        }
        Task task = taskMapper.selectByPrimaryKey(Long.valueOf(taskId));
        List<String> desFiles = JSONObject.parseObject(task.getDiscribFilePath(), List.class);
        List<String> exeFiles = JSONObject.parseObject(task.getExeFilePath(), List.class);
        List<String> total = new ArrayList<>(desFiles);
        total.addAll(exeFiles);

        FileHelper.checkDirectoryPath(tempDirectory);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String zipFileName = uid + "-" + taskId + "-" + formatter.format(new Date()) + ".zip";
        String zipFilePath = tempDirectory + zipFileName;


        ZipOutputStream zipOutputStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferedInputStream = null;
        File zipFile = new File(zipFilePath);


        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));

            for (String fileName : total) {
                String path = filePath + fileName;
                File file = new File(path);
                if (file.exists()) {
                    zipSource = new FileInputStream(file);
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zipOutputStream.putNextEntry(zipEntry);
                    bufferedInputStream = new BufferedInputStream(zipSource, 1024 * 10);
                    int read;
                    byte[] buf = new byte[1024 * 10];
                    while ((read = bufferedInputStream.read(buf, 0, 1024 * 10)) != -1) {
                        zipOutputStream.write(buf, 0, read);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }
                if (zipSource != null) {
                    zipSource.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (zipFile.exists()) {
            downloadZipFile(response, zipFileName, uid, taskId);
            zipFile.delete();
        }
    }

    PageInfo<TaskVo> getTaskVOPageInfo(Integer uid, PageInfo<Task> po) {
        return PageInfoUtil.convert(po, TaskVo.class);
    }

    void downloadZipFile(HttpServletResponse response, String fileName, Integer uid, Integer taskId) {
        if (fileName != null) {
            FileInputStream is = null;
            BufferedInputStream bs = null;
            OutputStream os = null;
            try {
                File file = new File(tempDirectory + fileName);
                if (file.exists()) {
                    response.setContentType("application/octet-stream;charset=utf-8");    //浏览器触发下载行为
//                    response.setCharacterEncoding("UTF-8");
                    //设置下载文件的名称，并转码
                    response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                    is = new FileInputStream(file);
                    bs = new BufferedInputStream(is);
                    os = response.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bs.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (bs != null) {
                        bs.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ResultVo<TestRecordVo> getTestRecord(Integer uid, Integer testrecordid) {
        TestRecord testRecord = testRecordMapper.selectByPrimaryKey(testrecordid.longValue());
        if (testRecord != null) {
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "测试记录查询成功", new TestRecordVo(testRecord));
        } else {
            return new ResultVo<>(Constant.REQUEST_FAIL, "测试记录不存在", null);
        }
    }

    @Override
    public List<Integer> getRecommendations(Integer taskId) {
        String taskDescriptor = taskDescriptorMapper.selectByTaskid(taskId);
        List<UserDescriptor> userDescriptorList = userDescriptorMapper.selectAll();
        if (userDescriptorList == null) {
            return new ArrayList<>();
        }
        HashMap<String, Integer> vector1 = JSONObject.parseObject(taskDescriptor, HashMap.class);

        HashMap<Integer, Double> result = new HashMap<>();   // 用户id ——》 相似度
        for (UserDescriptor userDescriptor : userDescriptorList) {
            Integer uid = userDescriptor.getUserid();
            HashMap<String, Integer> vector2 = JSONObject.parseObject(userDescriptor.getWords(), HashMap.class);
            Double sim = SimilarityMeasure.cosinSimilarity(vector1, vector2);
            result.put(uid, sim);
        }
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(result.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));  // 从大到小排序

        List<Integer> res = new ArrayList<>();
        // 用户人数不足，直接按顺序返回前十个
        if (list.size() <= 10) {
            for (Map.Entry<Integer, Double> entry : list) {
                res.add(entry.getKey());
            }
        } else {
            List<UserDescriptor> userDescriptorListTop = new ArrayList<>();
            if (list.size() > 20) {
                for (int i = 0; i < 20; i++) {
                    userDescriptorListTop.add(userDescriptorMapper.selectByUid(list.get(i).getKey()));    // 获取对应用户的描述性词
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    userDescriptorListTop.add(userDescriptorMapper.selectByUid(list.get(i).getKey()));    // 获取对应用户的描述性词
                }
            }
            res = findFarthest10(userDescriptorListTop);
        }
        return res;
    }

    public List<Integer> findFarthest10(List<UserDescriptor> userDescriptorListTop) {
        List<UserDescriptor> chosen = new ArrayList<>();
        if (userDescriptorListTop.size() <= 10) {
            chosen.addAll(userDescriptorListTop);
        } else {
            UserDescriptor top1 = userDescriptorListTop.get(0);     // 选取与任务最匹配的用户
            userDescriptorListTop.remove(top1);
            chosen.add(top1);
            while (chosen.size() != 10) {
                UserDescriptor userDescriptor = findFarthest(chosen, userDescriptorListTop);
                if (userDescriptor == null)
                    break;
                userDescriptorListTop.remove(userDescriptor);
                chosen.add(userDescriptor);
            }
        }
        List<Integer> res = new ArrayList<>();
        for (UserDescriptor userDescriptor : userDescriptorListTop) {
            res.add(userDescriptor.getUserid());
        }
        return res;
    }

    public UserDescriptor findFarthest(List<UserDescriptor> chosen, List<UserDescriptor> candidate) {
        UserDescriptor userDescriptor = null;
        double minDistance = Double.MAX_VALUE;
        for (UserDescriptor userDescriptor1 : candidate) {
            double distance = 0;
            HashMap<String, Integer> vector1 = JSONObject.parseObject(userDescriptor1.getWords(), HashMap.class);
            for (UserDescriptor selected : chosen) {
                HashMap<String, Integer> vector2 = JSONObject.parseObject(selected.getWords(), HashMap.class);
                distance += SimilarityMeasure.cosinSimilarity(vector1, vector2);    // 计算该用户与每一个已选用户的余弦相似度之和
            }
            if (distance < minDistance) {
                minDistance = distance;
                userDescriptor = userDescriptor1;
            }
        }
        return userDescriptor;
    }

    @Override
    public ResultVo<TaskVo> closeTask(Integer taskId) {
        Task task = taskMapper.selectByPrimaryKey(taskId.longValue());
        task.setFinishTime(new Date());
        if (taskMapper.updateByPrimaryKey(task) > 0) {
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "该任务已停止招募", new TaskVo(task));
        }
        return new ResultVo<>(Constant.REQUEST_FAIL, "服务器异常，关闭任务失败");
    }

    @Override
    public ResultVo<Boolean> checkIsClosed(Integer taskId) {
        Task task = taskMapper.selectByPrimaryKey(taskId.longValue());
        if (task == null) {
            return new ResultVo<>(Constant.REQUEST_FAIL, "该任务不存在");
        }
        if (task.getCurrentNum() >= task.getTotalNum() || task.getFinishTime().before(new Date())) {
//            System.out.println(task.getFinishTime().before(new Date()));
            return new ResultVo<>(Constant.REQUEST_SUCCESS, "任务已停止招募", Boolean.TRUE);
        }
        return new ResultVo<>(Constant.REQUEST_SUCCESS, "该任务尚在招募中", Boolean.FALSE);
    }
}

