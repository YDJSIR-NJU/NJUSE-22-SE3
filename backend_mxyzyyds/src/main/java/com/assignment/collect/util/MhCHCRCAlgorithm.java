package com.assignment.collect.util;

import com.assignment.collect.po.dupBug.DupBug;
import com.assignment.collect.po.reportReview.ReportReview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;



/*
 * Estimating Animal Abundance with Capture Frequency Data
 * N = S + f1*f1 / (2*f2)
 */

public class MhCHCRCAlgorithm {
    /**
     * @param captureProcess 该任务的所有报告评价
     * @param dupBugList     该任务下的所有重复的bug信息
     * @return
     */
    public static boolean obtainRecaptureResults(TreeMap<Integer, List<ReportReview>> captureProcess, List<DupBug> dupBugList) {
        if (captureProcess == null || dupBugList == null || captureProcess.size() == 0 || dupBugList.size() == 0) {
            return false;
        }

        HashMap<Integer, Integer> numForEachFreq = new HashMap<>();     // 出现K次的bug有多少个
        HashSet<Integer> distinctBugs = new HashSet<>();       // D  Actual number of bugs captured so far

        HashMap<Integer, Integer> dupBugHashMap = new HashMap<>();
        for (DupBug dupBug : dupBugList) {
            dupBugHashMap.put(dupBug.getReportid(), dupBug.getDupid());     // 该bug所属报告 --》 该bug与哪个报告的bug重复
        }

        HashMap<Integer, Integer> dupNum = new HashMap<>();  // 记录每个bug出现了多少次
        for (Integer cap : captureProcess.keySet()) {     // 对每个sample进行统计
            List<ReportReview> reviewList = captureProcess.get(cap);     // 每个sample的报告列表
            for (ReportReview reportReview : reviewList) {
                Integer dupTag = null;
                if (reportReview.getIsRepeatBug()) {    // 重复的bug
                    dupTag = dupBugHashMap.get(reportReview.getReportId().intValue()); // 如果是重复bug，tag是第一次发现此bug的id
                } else {
                    dupTag = reportReview.getReportId().intValue();        // 如果不是重复bug，可以理解为tag是自己的报告id
                }
                int count = 1;
                assert dupTag != null;
                if (dupNum.containsKey(dupTag))
                    count += dupNum.get(dupTag);
                dupNum.put(dupTag, count);

                distinctBugs.add(dupTag);   // 更新distinct的bug
            }
        }
        for (Integer dupTag : dupNum.keySet()) {
            Integer num = dupNum.get(dupTag);
            int count = 1;
            if (numForEachFreq.containsKey(num)) {
                count += numForEachFreq.get(num);   // 以bug出现的次数为key，以出现这么多次的bug数量为value
            }
            numForEachFreq.put(num, count);
        }
        double numDistinctBugs = 1.0 * distinctBugs.size();     // 独一无二的bug的数量
        double numCapturesize = 1.0 * captureProcess.size();   // t number of captures


        if (!numForEachFreq.containsKey(1)) {
            numForEachFreq.put(1, 0);
        }
        if (!numForEachFreq.containsKey(2)) {
            numForEachFreq.put(2, 0);
        }
        if (!numForEachFreq.containsKey(3)) {
            numForEachFreq.put(3, 0);
        }

        Double NValue = 0.0;
        if (numCapturesize * numForEachFreq.get(1) > 2 * numForEachFreq.get(2) && numCapturesize * numForEachFreq.get(2) > 3 * numForEachFreq.get(3)
                && 3 * numForEachFreq.get(1) * numForEachFreq.get(3) > 2 * numForEachFreq.get(2) * numForEachFreq.get(2)) {
            NValue = numDistinctBugs + ((1.0 * numForEachFreq.get(1) * numForEachFreq.get(1)) / (2.0 * numForEachFreq.get(2))) * (1.0 - 2.0 * numForEachFreq.get(2) /
                    (numCapturesize * numForEachFreq.get(1))) / (1.0 - 3.0 * numForEachFreq.get(3) / (numCapturesize * numForEachFreq.get(2)));
        } else {
            if (numForEachFreq.get(2) == 0)
                numForEachFreq.put(2, 1);
            NValue = numDistinctBugs + (1.0 * numForEachFreq.get(1) * numForEachFreq.get(1)) / (2.0 * numForEachFreq.get(2));
        }

        return (1.0 * numDistinctBugs / NValue) >= 0.90;       // 返回任务bug是否差不多查找完
    }

    public static void main(String args[]) {
    }
}
