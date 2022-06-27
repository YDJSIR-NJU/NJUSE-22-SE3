# COLLECT 后端

> 夏宇、余东骏、孟剑卫

## 访问地址

`xy.ydjsir.com.cn:8888`

注意到项目本身的端口被改到监听25565，因为YDJSIR处经常出现8000左右端口被全部占用的诡异情况。
文件会被持久化放在/var/www/collect下。该端口仅用于本地开发时直连远端测试用

## CICD

详情见`developdocs`下的项目设计文档。

## 鉴权

目前后端的所有接口，除了登录、注册和swagger，都需要鉴权。开发新的接口时请注意参考前面的接口，涉及UID应以token中携带的UID为准（`uid`），替换示例如下。

```java
@ApiOperation("setRead")
    @PostMapping("/read")
    public ResultVo<MessageVo> setRead(@RequestParam Long messageid, @RequestParam Long uid, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JWTUtils.header);
        String subject = JWTUtils.verify(token);
        uid = Long.parseLong(subject.split("_")[0]);

        assert messageid != null;
        return messageService.setRead(messageid, uid);
    }
```

除非修改`InterceptorConfig`，否则新添加的任何接口都是要鉴权的。详情见前端文档。