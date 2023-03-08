# PG数据库后端 对应 First 小程序
我的第一个PG数据库后端
## 设计模式
* MVC
* RESTFUL
## 项目结构
* com.east.laugh.pgmessageboard.Controller

Controller 控制器

Result 作为控制器返回的结果
* com.east.laugh.pgmessageboard.entity

实体类
Messageboard对应数据库Messageboard表，用于存放帖子

WechatResponse是后端向小程序返回的数据包装

* com.east.laugh.pgmessageboard.Mapper

Mybatis映射器，用于操作PG数据库。

