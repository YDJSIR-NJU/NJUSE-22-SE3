package com.assignment.collect.mapper.testRecord;

public class TestRecordMapperTests {
//    @Autowired
//    private TestRecordMapper testRecordMapper;
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private TaskMapper taskMapper;
//    int uid, tid;
//
//    @BeforeEach
//    void contextLoaders() {
//        createTask(new Task());
//        List<User> ulist = userMapper.selectAll();
//        uid = 6;
//        List<Task> tlist = taskMapper.selectAll();
//        int tid = tlist.get(0).getId();
//        System.out.println(uid);
//        System.out.println(tid);

//    }
//
//    @Test
//    void testInsert() {
//        TestRecord testRecord = new TestRecord();
//        testRecord = createTestRecord(testRecord, 12, true);
//        int res = testRecordMapper.insert(testRecord);
//        Assertions.assertTrue(res != 0);
//        testRecordMapper.deleteByPrimaryKey(12);
//    }
//
//    @Test
//    void testSelectAll() {

//        TestRecord testRecord = new TestRecord();
//        TestRecord testRecord1 = new TestRecord();
//        TestRecord testRecord2 = new TestRecord();
//        testRecord = createTestRecord(testRecord, 19, true);
//        testRecord1 = createTestRecord(testRecord1, 18, true);
//        testRecord2 = createTestRecord(testRecord2, 17, true);
//        testRecordMapper.insert(testRecord);
//        testRecordMapper.insert(testRecord1);
//        testRecordMapper.insert(testRecord2);
//        List<TestRecord> list = testRecordMapper.selectAll();
//        int result = 0;
//        for (TestRecord tr : list
//        ) {
//            if (tr.getId() >= 17 && tr.getId() <= 19) result++;
//
//        }
//        Assertions.assertTrue(result == 3);
//        testRecordMapper.deleteByPrimaryKey(17);
//        testRecordMapper.deleteByPrimaryKey(18);
//        testRecordMapper.deleteByPrimaryKey(19);

//
//    }
//
//    @Test
//    void testdeleteByPrimaryKey() {
//        TestRecord testRecord = new TestRecord();
//        testRecord = createTestRecord(testRecord, 19, true);
//        testRecordMapper.insert(testRecord);
//        int res = testRecordMapper.deleteByPrimaryKey(19);
//        Assertions.assertTrue(res != 0);
//
//    }
//    @Test
//    void testselectByPrimaryKey(){
//        TestRecord testRecord = new TestRecord();
//        testRecord = createTestRecord(testRecord, 200, true);
//        testRecordMapper.insert(testRecord);
//        int res=testRecordMapper.selectByPrimaryKey(200).get(0).getId();
//        Assertions.assertEquals(200,res);
//        testRecordMapper.deleteByPrimaryKey(200);
//    }
//    @Test
//    void testselectByTaskIdAndUid(){
//        Task task1=new Task();
//        task1=createTask(task1);
//        task1.setId(200);
//        TestRecord testRecord=new TestRecord();
//        testRecord=createTestRecord(testRecord,300,false);
//        taskMapper.insert(task1);
//        testRecordMapper.insert(testRecord);
//        List<TestRecord>lists=testRecordMapper.selectByTaskIdAndUid(200,6);
//        boolean flag=true;
//        for (TestRecord tr:lists
//             ) {
//            if(!(tr.getTaskId()==200&&tr.getUserId()==6))flag=false;
//        }
//        Assertions.assertEquals(true,flag);
//        testRecordMapper.deleteByPrimaryKey(300);
//        taskMapper.deleteByPrimaryKey(200);
//
//    }
//    private TestRecord createTestRecord(TestRecord testRecord, int random, boolean finish) {

//        testRecord.setTaskId(43);
//        testRecord.setUserId(6);
//        testRecord.setId((long) random);
//        testRecord.setFinishTime(new Date(0));
//        testRecord.setCommitTime(new Date());
//        testRecord.setFinished(finish);
//        return testRecord;
//        return null;
//    }
//
//    private Task createTask(Task task2) {
//
//        task2.setUserId(6L);
//        task2.setType("FUNCTION");
//        task2.setStartTime(new Date());
//        task2.setFinishTime(new Date(0));
//        task2.setCurrentNum(12);
//        task2.setTotalNum(13);
//        task2.setExeFilePath("exe");
//        task2.setTaskDiscribe("hah");
//        task2.setDiscribFilePath("dis");
//        return task2;
//    }
}
