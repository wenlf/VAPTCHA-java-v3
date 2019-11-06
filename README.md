# VAPTCHA-java-v3


## Quick Start

1.创建验证单元，获取`VID`和`Key` 。[点击创建](https://management.vaptcha.com/manage)。

2.替换代码中的`VID`和`Key`

前端

```javascript
// vaptcha-sdk/src/main/resources/static/xxx.html

<script>
    vaptcha({
        vid: '*****', // 替换为自己的VID
    	offline_server: 'http://127.0.0.1:8080/offline', //离线模式服务端地址 
     	...
    }).then(function (vaptchaObj) {
      ...
</script>
```

后端

```java
// vaptcha-sdk/src/main/java/com/vaptcha/constant/Constant.java

//-----------验证单元相关信息 请自行替换----------------
    // 验证单元key
    public static final String SecretKey = "****"; 
    // 验证单元id
    public static final String Vid = "****"; 
```

3.测试地址

* 点击式：`localhost:8080/click`
* 嵌入式：`localhost:8080/embed`
* 隐藏式：`localhost:8080/invisible`

