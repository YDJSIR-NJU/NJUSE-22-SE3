package com.assignment.collect.mapper.task;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskMapperTests {
//    @Autowired
//    private TaskMapper taskMapper;
//    @Autowired
//    private TestRecordMapper testRecordMapper;
//
//
//    @Test
//    void testInsert() {
//        Task task = new Task();
//        task = createTask(task, 200, 14);
//        int res = taskMapper.insert(task);
//        Assertions.assertTrue(res != 0);
//        taskMapper.deleteByPrimaryKey(200);
//    }
//
//    @Test
//    void testselectByPrimaryKey() {
//        Task task = new Task();
//        task = createTask(task, 200, 14);
//        Task task1 = new Task();
//        task1 = createTask(task1, 201, 15);
//        taskMapper.insert(task);
//        taskMapper.insert(task1);
//        Assertions.assertEquals(200, (int) taskMapper.selectByPrimaryKey(200).getId());
//        Assertions.assertEquals(201, (int) taskMapper.selectByPrimaryKey(201).getId());
//        taskMapper.deleteByPrimaryKey(200);
//        taskMapper.deleteByPrimaryKey(201);
//
//
//    }
//
//    @Test
//    void testselectAll() {
//        Task task = new Task();
//        task = createTask(task, 200, 14);
//        Task task1 = new Task();
//        task1 = createTask(task1, 201, 15);
//        Task task2 = new Task();
//        task2 = createTask(task2, 202, 15);
//        Task task3 = new Task();
//        task3 = createTask(task3, 203, 15);
//        taskMapper.insert(task);
//        taskMapper.insert(task1);
//        taskMapper.insert(task2);
//        taskMapper.insert(task3);
//        List<Task> lists = taskMapper.selectAll();
//        int n = 0;
//        for (Task t : lists
//        ) {
//            if (t.getId() >= 200 && t.getId() <= 203) n++;
//        }
//        Assertions.assertEquals(4, n);
//        taskMapper.deleteByPrimaryKey(200);
//        taskMapper.deleteByPrimaryKey(201);
//        taskMapper.deleteByPrimaryKey(202);
//        taskMapper.deleteByPrimaryKey(203);
//
//    }
//
//    @Test
//    void testselectByUid() {
//        Task task = new Task();
//        task = createTaskuid(task, 200, 14, 7);
//        Task task1 = new Task();
//        task1 = createTaskuid(task1, 201, 15, 7);
//        Task task2 = new Task();
//        task2 = createTaskuid(task2, 202, 15, 8);
//        Task task3 = new Task();
//        task3 = createTaskuid(task3, 203, 15, 8);
//        taskMapper.insert(task);
//        taskMapper.insert(task1);
//        taskMapper.insert(task2);
//        taskMapper.insert(task3);
//        List<Task> ts = taskMapper.selectByUid(7);
//        List<Task> ts1 = taskMapper.selectByUid(8);
//        int n = 0;
//        for (Task t : ts
//        ) {
//            if (t.getId() >= 200 && t.getId() <= 201) n++;
//        }
//        for (Task t : ts1
//        ) {
//            if (t.getId() >= 202 && t.getId() <= 203) n++;
//        }
//        Assertions.assertEquals(4, n);
//        taskMapper.deleteByPrimaryKey(200);
//        taskMapper.deleteByPrimaryKey(201);
//        taskMapper.deleteByPrimaryKey(202);
//        taskMapper.deleteByPrimaryKey(203);
//
//    }
//
//    @Test
//    void testselectProcessByUid() {
//        Task task = new Task();
//        task = createTaskuid(task, 200, 14, 6);
//        TestRecord test1 = new TestRecord();
//        test1 = createTestRecord(test1, 200, false, 200);
//        taskMapper.insert(task);
//        testRecordMapper.insert(test1);
//        List<Task> tasks = taskMapper.selectProcessingByUid(6);
//        Assertions.assertNotNull(tasks);
//        for (Task t: tasks) {
//             List<TestRecord>testRecords=testRecordMapper.selectByTaskIdAndUid(t.getId(),6);
//             boolean flag=false;
//             for(TestRecord tr:testRecords){
//                 if(tr.getFinished()==false)flag=true;
//             }
//               Assertions.assertTrue(flag);
//
//        }
//        testRecordMapper.deleteByPrimaryKey(200);
//        taskMapper.deleteByPrimaryKey(200);
//
//    }
//
//    @Test
//    void testselectHistoryByUid() {
//        Task task = new Task();
//        task = createTaskuid(task, 200, 14, 6);
//        TestRecord test1 = new TestRecord();
//        test1 = createTestRecord(test1, 200, true, 200);
//        taskMapper.insert(task);
//        testRecordMapper.insert(test1);
//        List<Task> tasks = taskMapper.selectProcessingByUid(6);
//        Assertions.assertNotNull(tasks);
//        for (Task t: tasks) {
//            List<TestRecord>testRecords=testRecordMapper.selectByTaskIdAndUid(t.getId(),6);
//            boolean flag=false;
//            for(TestRecord tr:testRecords){
//                if(tr.getFinished()==true)flag=true;
//            }
//            Assertions.assertTrue(flag);
//
//        }
//        testRecordMapper.deleteByPrimaryKey(200);
//        taskMapper.deleteByPrimaryKey(200);
//    }
//
//    @Test
//    void todoList() {
//        List<Task> todolist = taskMapper.todoList();
//        for (Task i : todolist) {
//            Assertions.assertTrue(i.getCurrentNum() < i.getTotalNum());
//        }
//    }
//
//    Task createTask(Task task, int random, int num) {
//        task.setId(random);
//        task.setCurrentNum(12);
//        task.setTotalNum(num);
//        task.setUserId(6);
//        task.setDiscribFilePath("http");
//        task.setExeFilePath("exe");
//        task.setStartTime(new Date());
//        task.setFinishTime(new Date(0));
//        task.setType("FUNCTION");
//        task.setTaskDiscribe("discription");
//        return task;
//    }
//
//
//    Task createTaskuid(Task task, int random, int num, int uid) {
//        task.setId(random);
//        task.setCurrentNum(12);
//        task.setTotalNum(num);
//        task.setUserId(uid);
//        task.setDiscribFilePath("http");
//        task.setExeFilePath("exe");
//        task.setStartTime(new Date());
//        task.setFinishTime(new Date(0));
//        task.setType("FUNCTION");
//        task.setTaskDiscribe("discription");
//        return task;
//    }
//
//    private TestRecord createTestRecord(TestRecord testRecord, int random, boolean finish, int taskid) {
//
//        testRecord.setTaskId(taskid);
//        testRecord.setUserId(6);
//        testRecord.setId(random);
//        testRecord.setFinishTime(new Date(0));
//        testRecord.setCommitTime(new Date());
//        testRecord.setFinished(finish);
//        return testRecord;
//    }

}
