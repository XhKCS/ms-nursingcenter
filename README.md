# ms-nursingcenter
颐养中心后端 - 微服务（Micro-service）版

- 使用`nacos`进行服务注册和发现，对服务统一管理；
- 使用`OpenFeign`实现服务之间的远程调用；
- 通过网关`gateway`实现对各个服务使用同一端口访问，`gateway`会自动进行服务转发；
- 另外，可以通过`Sentinel`监控各个服务处理请求的状态，并可进行流量控制等操作；

在启动该微服务版项目之前，请先配置好**nacos**，将nacos的服务端口设置为`10823`；

## 前端请求方法
前端请求方法基本不变，只需将请求的**baseURL的端口**修改为微服务网关的端口：`11000`；

另外再改变一下**响应拦截器**的处理方法，不再判断响应是否是`invalid token`，而是判断响应错误的状态码是否是**401（Unauthorized）**；

## 服务划分
该微服务项目将原版项目划分成了以下五个微服务模块：
### (1) gateway
网关服务，负责处理统一的访问请求，解析请求的URL并转发至相应的微服务模块；网关端口为：`11000`。

Redis和JWT也被拆分到了该服务模块中，用于网关的鉴权，会拦截未经过登录授权的访问。
### (2) core-server
该模块包括用户、客户、床位、退住登记、外出登记相应业务模块；
### (3) nursing-server
该模块为护理相应业务模块，包括护理级别、护理项目、客户购买的护理服务、护理记录等；
### (4) meal-server
该模块为膳食相应业务模块，包括食品、膳食日历项、膳食预定等；
### (5) tool-server
该模块为相对独立的公共工具服务模块，目前主要包括WebSocket与AI对话功能。

## 接口文档及调试地址
由于业务功能被划分成了三个服务模块，所以对应的接口文档也被划分成了三部分，通过不同的端口访问：
### (1) core-server
用户、客户、床位、退住登记、外出登记相应业务模块，接口文档地址为 http://localhost:11010/doc.html
 或  http://localhost:11010/swagger-ui/index.html

### (2) nursing-server
护理相应业务模块，接口文档地址为 http://localhost:11030/doc.html
或  http://localhost:11030/swagger-ui/index.html

### (3) meal-server
膳食相应模块，接口文档地址为 http://localhost:11040/doc.html
或  http://localhost:11040/swagger-ui/index.html



本项目的非微服务版本地址如下：
https://github.com/XhKCS/nursingcenter.git
