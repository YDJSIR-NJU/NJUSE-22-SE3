package com.assignment.collect.util;

import com.assignment.collect.enums.ExceptionType;
import com.assignment.collect.exception.MyException;
import com.assignment.collect.vo.file.FileInfoVo;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XiaYu
 * @Date 2022/2/25 20:59
 */
public class FileHelper {

    public static List<FileInfoVo> save(String path, MultipartFile[] files) throws IOException {
        if (!checkDirectoryPath(path)) {
            throw new MyException(ExceptionType.SERVER_ERROR, "服务器错误， 存放上传文件的文件夹不存在或创建失败！");
        }

        List<FileInfoVo> fileInfoVoList = new ArrayList<>();

        for (MultipartFile file : files) {
            // 根据目标地址构造文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream(path + file.getOriginalFilename());
            // 将文件复制到映射的地址
            FileCopyUtils.copy(file.getInputStream(), fileOutputStream);

            String name = file.getOriginalFilename();
            String type = name.substring(name.lastIndexOf(".") + 1);
            String size = String.format("%.2f", (file.getSize() * 1.0 / 1024 / 1024)) + " MB";

            FileInfoVo fileInfoVO = new FileInfoVo(name, type, size, file.getOriginalFilename());
            fileInfoVoList.add(fileInfoVO);
        }
        return fileInfoVoList;
    }

    public static Resource loadFileAsResource(String path) {
        try {
            Path filePath = Paths.get(path);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            throw new MyException(ExceptionType.SERVER_ERROR, "服务端错误，加载文件资源时出错！");
        }
        return null;
    }

    public static boolean checkDirectoryPath(String path) {
        File dir = new File(path);
        // 如果文件夹不存在则创建
        if (!dir.exists() || !dir.isDirectory()) {
            return dir.mkdirs();
        }
        return true;
    }
}
