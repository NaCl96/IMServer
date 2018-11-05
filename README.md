# IMServer
# 即时通讯系统服务器


本项目是一个类微信的即时通讯系统，用来实现用户之间即时通讯。
本系统分为Android客户端和后台服务器两个部分。此项目为本系统的后台服务器部分。用户发送的消息通过Socket发送到服务器，服务器作为中转将消息通过Socket转发到目标用户。
服务器实现了登录验证，返回好友数据，中转聊天消息的功能。


## Credits
此项目小组成员为以下5名同学：

* 覃昌雷
* 康宗
* 石岩
* 姜洪超
* 冯会会


## 配置说明
* 本服务器使用Eclipse for Java EE开发。
* 本即时通讯系统分为Android客户端和后台服务器两个部分。本系统的客户端部分请见：https://github.com/NaCl96/IMClient
* 服务器以Tomcat 9.0作为Servlet容器，需在Tomcat 9.0下运行
* 服务器用MySQL数据库来存储数据，需安装MySQL数据库并配置JDBC驱动
* 根目录下im001_user.sql和im001_friendship.sql为数据库备份文件，可导入到MySQL


## 功能模块

### 登录验证和返回好友数据
* LoginServlet用来验证用户登录消息。收到用户发来的用户ID和密码的表单数据后，根据用户ID在数据库中的查询该用户的正确密码并与发来的密码进行比较，将验证结果返回。
* FriendServlet根据用户ID在数据库中查询该用户的好友信息，将好友数据以JSON格式返回。

### 数据库连接和查询
* JDBCUtil封装连接数据库和查询数据的基本操作
* FriendDAO作为查询数据库中好友关系表的接口

### Socket通信模块
* SocketServiceLoader实现ServletContextListener接口。当Tomcat启动时，启动SocketThread线程。SocketThread线程建立ServerSocket，每当ServerSocket建立一个新Socket连接时为该Socket启动一个新的SocketOperate线程处理该Socket。
* SocketOperate线程中循环读取Socket输入流。当Socket收到用户ID信息后，将用户ID和此Socket以键值对形式存入HashMap中,为此Socket建立索引。
* 用户向好友发送的消息有发起用户ID、目标用户ID、消息正文三段数据，以JSON格式封装。Socket收到消息后解析JSON数据，获取目标用户ID，根据目标用户ID从HashMap获取目标用户的Socket，通过此Socket将消息发送到目标用户。
