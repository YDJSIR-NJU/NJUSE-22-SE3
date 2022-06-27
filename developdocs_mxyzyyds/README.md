# COLLECT迭代三文档说明

我们的选题是COLLECT，迭代三专攻的方向是众测工人评分框架。

## 项目访问方式

生产环境（网站）访问链接：https://se3.ydjsir.com.cn/

HTTP请求会被重定向到HTTPS以实现强制HTTPS访问。

==在实际访问网站时，由于报告协作、报告推荐等功能需要一定的已有数据，否则难以体现效果，因此请检查时参考网站前端和`项目设计文档`内`4.1用户界面层分解`的截图==

## 构建过程基本说明

详情参见本目录下的`部署文档`。

## Jenkins访问地址

详情参见本目录下的`部署文档`。

## 代码仓库列表

### 概述

所有的构建状态都会推回南大Git而非课程仓库中。由于==后端项目仓库==和==开发文档仓库==内含有凭据，直接公开可能被人利用，因而不直接公开。

以上的三个项目都实现了CICD，并能推回构建状态，其中前端项目和Python服务项目已在南大Git上对所有已登录用户公开，可以直接访问，查看CI/CD状态。下面提供这三个项目的CI/CD状态推回情况。

### 后端（`maven-wrapper/3.1.0*maven 3.8.4 + OpenJDK 1.8 Docker`）

https://git.nju.edu.cn/YDJSIR/191250186_mxyzyyds_backend_mxyzyyds/-/pipelines

需要向余东骏申请查看仓库的权限。

![image-20220604171118271](https://ydjsir-edu.oss-cn-shanghai.aliyuncs.com/SE3/pictures/image-20220604171118271.png)

### 前端（`Node x86_64 v14.18.3+Nginx 1.19 Docker`）构建状态推回展示

https://git.nju.edu.cn/YDJSIR/191250186_mxyzyyds_frontend_mxyzyyds/-/pipelines

可以在登录南大Git后直接访问。

![image-20220604165357504](https://ydjsir-edu.oss-cn-shanghai.aliyuncs.com/SE3/pictures/image-20220604165357504.png)

### Python项目（`Python 3.9 Docker`）构建状态推回展示

https://git.nju.edu.cn/YDJSIR/191250186_mxyzyyds_python_mxyzyyds/-/pipelines

![image-20220604165308501](https://ydjsir-edu.oss-cn-shanghai.aliyuncs.com/SE3/pictures/image-20220604165308501.png)

构建好后（包括构建成功状态推回南大Git后），容器仍需要启动后初始化从境外服务器加载预训练模型，此过程耗时长（10分钟至无限，如因网络超时，容器会无限重试连接），且很可能失败。如果要触发构建，可以联系余东骏同步查看容器镜像内部的日志输出情况，以确认其状况。目前这个容器的运行状态是好的，我们也可以提供正常运行的容器的备份镜像文件。

## 算法说明

详情参见这三个文档。

- `报告能力词云与报告聚类算法说明`
- `任务-用户推荐算法说明`
- `众测工人评分框架`