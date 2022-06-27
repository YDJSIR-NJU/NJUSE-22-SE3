package com.assignment.collect.vo.file;

import lombok.Data;

/**
 * @Author: XiaYu
 * @Date 2022/2/25 21:02
 */
@Data
public class FileInfoVo {
    private String name;
    private String type;
    private String size;
    private String path;

    public FileInfoVo(String name, String type, String size, String path) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.path = path;
    }
}
