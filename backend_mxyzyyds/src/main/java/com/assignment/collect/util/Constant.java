package com.assignment.collect.util;

import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @Author: XiaYu
 * @Date 2022/2/19 15:37
 */
public class Constant {

    //请求失败返回的状态码
    public static final Integer REQUEST_FAIL = 0;

    //请求成功返回的状态码
    public static final Integer REQUEST_SUCCESS = 1;

    //服务器内部错误返回的状态码
    public static final Integer REQUEST_ERROR = 2;

    //token无效
    public static final Integer TOKEN_ERROR_EXCEPTION = 3;

    //token超时
    public static final Integer TOKEN_EXPIRED_EXCEPTION = 4;

    //默认异常
    public static final Integer DEFAULT_EXCEPTION = 5;

    //审核通过一份报告的分数
    public static final Integer ACCEPTED_REPORT_SCORE = 10;

    //发现新的BUG的分数
    public static final Integer NEWBUG_SCORE = 10;

    //审核不过报告被扣的分数
    public static final Integer REJECTED_REPORT_SCORE = 10;

    //发一条补充报告的分数
    public static final Integer ADDREPORT_SCORE = 3;

    //给别人报告写评价的分数
    public static final Integer REMARK_SCORE = 1;


    //一页的大小
    public static final Integer PAGE_SIZE = 10;
    public static final String IMAGE_PATTERN = "(png|jpe?g|gif)";
    public static final String filePath;
    public static final String tempDirectory;
    public static final String configDirectory;
    public static final String recommendConfig1 = "recommendConfig1";
    public static final String NConfig = "nconfig";
    public static final String recommendConfig2 = "recommendConfig2";
    public static final String PYTHON_BACKEND;
    public static HashMap<String, String> similarityAlgorithms = new HashMap<>();
    public static Double REJECTEDSCORE = 0.9;

    public static Double COOPERATIONMIN = 0.88;


    static {
        ResourceBundle bundle = ResourceBundle.getBundle("Files");
        if (File.separator.equals("/")) {
            filePath = bundle.getString("LINUX_FILEPATH");
            tempDirectory = bundle.getString("LINUX_TEMP");
            configDirectory = bundle.getString("LINUX_CONFIG");
            PYTHON_BACKEND = bundle.getString("LINUX_PYTHON_BACKEND");
        } else {
            filePath = bundle.getString("WINDOWS_FILEPATH");
            tempDirectory = bundle.getString("WINDOWS_TEMP");
            configDirectory = bundle.getString("WINDOWS_CONFIG");
            PYTHON_BACKEND = bundle.getString("WINDOWS_PYTHON_BACKEND");
        }
        similarityAlgorithms.put("TF-IDF", "cossim");
        similarityAlgorithms.put("Text2Vec", "text2vec");
    }

}
