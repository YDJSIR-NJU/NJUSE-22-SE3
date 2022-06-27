package com.assignment.collect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: XiaYu
 * @Date 2022/2/25 20:46
 */
@Configuration
public class WebResourceConfig implements WebMvcConfigurer {
//    @Value("${web.file-upload-path}")
//    private String fileUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResourceHandler: 配置资源映射路径
        // addResourceLocations: 文件上传保存的路径
//        registry.addResourceHandler("/file/**").addResourceLocations("file:" + fileUploadPath);
    }
}
