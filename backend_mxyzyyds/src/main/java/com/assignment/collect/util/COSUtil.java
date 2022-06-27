package com.assignment.collect.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.util.Date;
import java.util.ResourceBundle;

public class COSUtil {
    public static final String REGION;

    public static final String bucketName;
    public static final COSClient cosClient;
    private static final String SecretKey;

    // https://citi-1308543285.cos.ap-shanghai.myqcloud.com/var/local/excel/2021-01-ESG-1644458156386.xlsx
    private static final String AccessKey;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("COS");
        SecretKey = bundle.getString("SecretKey");
        AccessKey = bundle.getString("AccessKey");
        REGION = bundle.getString("REGION");
        bucketName = bundle.getString("bucketName");
        cosClient = createCli();
    }

    static COSClient createCli() {
        return createCli(REGION);
    }

    static COSClient createCli(String region) {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(AccessKey, SecretKey);
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 生成cos客户端
        return new COSClient(cred, clientConfig);
    }

    /**
     * 上传一个文件
     *
     * @param localPath 本地路径
     * @param destPath  这里要写完整的带文件名的路径
     */
    public static void uploadObject(String localPath, String destPath) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setHeader("expires", new Date(1660000000000L));

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, destPath, new File(localPath));
        putObjectRequest.withMetadata(objectMetadata);

        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        System.out.println(putObjectResult.getRequestId());

//        cosClient.shutdown();
    }

    // 仅用作测试,本地可以改成你想要的文件,服务器端没有那个文件夹的话他会自己新建的
    public static void main(String[] args) {
        uploadObject("E:\\Lab4\\submit.zip", "/submit.zip");
    }
}
