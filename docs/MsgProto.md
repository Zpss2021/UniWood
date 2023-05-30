# 协议消息串

## 1. 消息格式

- `命令（Command枚举类)`
- `命令（Command枚举类）| 消息参数1 | 消息参数2 | ...`

## 2. 详细命令

### 2.1 客户端发出

- 用户登录：`LOGIN | 用户名 | 密码`
- 用户注册：`REGISTER | 用户名 | 密码 | 大学 | 头像base64`
- 获取大学列表：`UNIV_LIST`
- 用户下线：`LOGOUT`

[//]: # (- 用户下线：`LOGOUT`)

### 2.2 服务器端发出

- 登录成功：`LOGIN_SUCCESS | 用户ID | 用户名 | 大学 | 头像base64`
- 登录失败：`LOGIN_FAILED | 原因短语`
- 注册成功：`REGISTER_SUCCESS | 用户ID | 用户名 | 大学 | 头像base64`
- 注册失败：`REGISTER_FAILED | 原因短语`
- 大学列表：`UNIV_LIST | 大学1 | 大学2 | ...`
- 未知命令：`UNKNOWN`