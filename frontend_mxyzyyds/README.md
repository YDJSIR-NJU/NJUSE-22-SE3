# COLLECT-前端

> 张博雅、余东骏

## 访问地址

`se3.ydjsir.com.cn`

注意到项目本身的端口用`npm run serve`启动时占用的是`8080`（如被占用会向上递增），而在容器里启动的时候占据的是容器的80和443端口（支持HTTPS访问，HTTP访问会被重定向到HTTPS）。修改`vue.config.js`里面的对应后端地址只影响本地开发，不影响线上部署。

## CICD

详情见`developdocs`下的项目设计文档。

## 鉴权

目前后端的所有接口，除了登录、注册和swagger，都需要鉴权。鉴权的方式是看HTTP请求里面的`headers`里面的`Authorization`字段。这个token在鉴权以外还保存了用户的角色等信息。不过这里面具体保存的信息全部由后端进行解析和处理，对前端透明。前端会将token存储到`localstorage`中，即使关闭浏览器仍能保存。

![image-20220519170802978](https://ydjsir-edu.oss-cn-shanghai.aliyuncs.com/SE3/pictures/image-20220519170802978.png)

目前设定上token的过期时间是30分钟。不过，用户如果还有和服务端的通信的话，如果后端发现客户端传来的token的有效期小于15分钟，则会返回一个新的token。也就是说，当用户不和服务端进行通信15~30分钟之后，原有token会失效，需要重新登陆获取token。夏宇强烈建议不要试图关闭鉴权，否则上线时很可能会忘记设置。

如何添加鉴权？请确保在`api`包下面的`js`在请求需要鉴权的接口的时候要带上`token`。下面为post请求和get请求各提供一个示例。

```javascript
// 注意，GET是url到config这样的结构
export const getAllMessages = (uid, page) => {
  return axios.get(`${MESSAGE_MODULE}/get/${uid}?page=${page}`,
    {
      headers: {
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
    updateToken(response)
    return response
  })
}
export const setRead = (messageid, uid) => {
    // 注意，POST是url、data、到config这样的结构，POST数据体为空时不要忘了写一对空的花括号占位置
  return axios.post(`${MESSAGE_MODULE}/read?messageid=${messageid}&uid=${uid}`,{},// 注意
    {
      headers: {
        Authorization: window.localStorage.getItem("token")
      }
    }
  ).then(response => {
    updateToken(response)
    return response
  })
}
```

上面的代码的`updateToken`函数中，如果请求返回带有新的token，则会更新token。如果返回的`ResultVo`的`code`是3或4，即token相关的错误，则会将页面重定向到登陆页面。

```javascript
export const updateToken = (response) => {
  if (response.data.code === 3 || response.data.code === 4) {
    // alert("请重新登陆！")
    router.push("/login")
  }
  if (response.headers['authorization'] != null) {
    console.log("Token Updated!")
    window.localStorage.setItem('token', response.headers['authorization'])
  }
}
```

## 开发建议

### 尽可能高的复用

不只是节省工作量，更是要保证前后的一致性还有通用性。有好的方案应尽快全面推广。

### 明确设计风格

UI的风格要明确。尽可能使用扁平化的设计风格，多使用框架组件。

### 尽可能多的边界检验

Corner Case不能被忽略。

### 尽可能多展示信息

后端如果传到了前端的内容应尽可能展示。