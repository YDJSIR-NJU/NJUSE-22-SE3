package com.assignment.collect.mapper.defectReport;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DefectReportMapperTests {
//    @Autowired
//    private TestRecordMapper testRecordMapper;
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private TaskMapper taskMapper;
//    @Autowired
//    private DefectReportMapper defectReportMapper;
//
//    @Test
//    void testInsert(){
//      DefectReport defectReport=new DefectReport();
//      defectReport=createDefectReport(defectReport,14);
//      int res=defectReportMapper.insert(defectReport);
//        Assertions.assertEquals(1, res);
//        defectReportMapper.deleteByPrimaryKey(14);
//    }
//
//    @Test
//    void testselectAll(){
//    DefectReport d1=new DefectReport();
//    DefectReport d2=new DefectReport();
//    DefectReport d3=new DefectReport();
//    d1=createDefectReport(d1,55);
//    d2=createDefectReport(d2,56);
//    d3=createDefectReport(d3,57);
//    defectReportMapper.insert(d1);
//    defectReportMapper.insert(d2);
//    defectReportMapper.insert(d3);
//    List<DefectReport> lists=defectReportMapper.selectAll();
//    int num=0;
//        for (DefectReport d:lists
//             ) {
//            if(d.getId()>=55&&d.getId()<=57)num++;
//        }
//        Assertions.assertEquals(3, num);
//
//        defectReportMapper.deleteByPrimaryKey(55);
//        defectReportMapper.deleteByPrimaryKey(56);
//        defectReportMapper.deleteByPrimaryKey(57);
//        }
//
//    @Test
//    void testDeleteByPrimaryKey(){
//        DefectReport defectReport=new DefectReport();
//        defectReport=createDefectReport(defectReport,14);
//        defectReportMapper.insert(defectReport);
//        int res=defectReportMapper.deleteByPrimaryKey(14);
//        Assertions.assertEquals(1, res);
//
//    }
//
//    @Test
//    void testselectByPrimaryKey(){
//        TestRecord testRecord=new TestRecord();
//        testRecord=createTestRecord(testRecord,200,false);
//        DefectReport defectReport=new DefectReport();
//        defectReport=createDefectReport(defectReport,200);
//        defectReport.setTestRecordId(200);
//        testRecordMapper.insert(testRecord);
//        defectReportMapper.insert(defectReport);
//        Assertions.assertEquals(200,defectReportMapper.selectByPrimaryKey(200).get(0).getId());
//        defectReportMapper.deleteByPrimaryKey(200);
//        testRecordMapper.deleteByPrimaryKey(200);
//    }
//  @Test
//  void testselectByTestRecordId(){
//        TestRecord testRecord=new TestRecord();
//        testRecord=createTestRecord(testRecord,200,false);
//        DefectReport defectReport=new DefectReport();
//        defectReport=createDefectReport(defectReport,200);
//        defectReport.setTestRecordId(200);
//        testRecordMapper.insert(testRecord);
//        defectReportMapper.insert(defectReport);
//        List<DefectReport>lists=defectReportMapper.selectByTestRecordId(200);
//        boolean flag=false;
//        for (DefectReport dr:lists
//           ) {
//          if(dr.getId()==200)flag=true;
//      }
//      Assertions.assertEquals(true,flag);
//        defectReportMapper.deleteByPrimaryKey(200);
//        testRecordMapper.deleteByPrimaryKey(200);
//
//
//  }
//  @Test
//  void testselectByTaskid(){
//        Task task=new Task();
//        task=createTaskuid(task,200,12,6);
//        TestRecord testRecord=new TestRecord();
//        testRecord=createTestRecord(testRecord,200,false);
//        testRecord.setTaskId(200);
//        DefectReport defectReport=new DefectReport();
//        defectReport=createDefectReport(defectReport,200);
//        defectReport.setTestRecordId(200);
//        taskMapper.insert(task);
//        testRecordMapper.insert(testRecord);
//        defectReportMapper.insert(defectReport);
//        List<DefectReport>delist=defectReportMapper.selectByTaskId(200);
//        List<TestRecord>telist=testRecordMapper.selectByTaskIdAndUid(200,6);
//
//        boolean flag=false;
//      for (DefectReport df:delist
//           ) {
//          for (TestRecord tr:telist
//               ) {
//              if(df.getTestRecordId().compareTo(tr.getId())==0)flag=true;
//          }
//      }
//    Assertions.assertEquals(true,flag);
//      defectReportMapper.deleteByPrimaryKey(200);
//      testRecordMapper.deleteByPrimaryKey(200);
//      taskMapper.deleteByPrimaryKey(200);
//
//
//  }
//    DefectReport createDefectReport(DefectReport defectReport,int random){
//        defectReport.setId(random);
//        defectReport.setScreenshotPathList("test");
//        defectReport.setDescription("hahha");
//        defectReport.setTestRecordId(9);
//        defectReport.setCreateTime(new Date());
//        defectReport.setDeviceBrand("DELL");
//        defectReport.setOperatingSystem("LINUX");
//
//        return  defectReport;
//    }
//    private TestRecord createTestRecord(TestRecord testRecord, int random, boolean finish) {
//
//        testRecord.setTaskId(43);
//        testRecord.setUserId(6);
//        testRecord.setId(random);
//        testRecord.setFinishTime(new Date(0));
//        testRecord.setCommitTime(new Date());
//        testRecord.setFinished(finish);
//        return testRecord;
//    }
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
}
