# 协议消息串

## 1. 消息格式

- `命令（Command枚举类)`
- `命令（Command枚举类）| 消息参数1 | 消息参数2 | ...`

## 2. 详细命令

### 2.1 客户端发出

- 用户登录：`LOGIN | 用户名 | 密码`
- 用户下线：`LOGOUT`
- 心跳：`HEARTBEAT`
- 用户注册：`REGISTER | 用户名 | 密码 | 大学 | 头像base64`
- 获取大学列表：`UNIV_LIST`
- 获取用户信息：`USER_INFO | 用户ID`
- 获取关注列表：`FOLW_LIST | 用户ID`
- 获取粉丝列表：`FANS_LIST | 用户ID`
- 获取发贴列表：`POST_LIST | 用户ID`
- 修改个人信息：`EDIT_INFO | 用户ID | 用户名 | 密码 | 大学 | 头像base64`

[//]: # (- 用户下线：`LOGOUT`)

### 2.2 服务器端发出

- 登录成功：`LOGIN_SUCCESS | 用户ID | 用户名 | 大学 | 头像base64`
- 登录失败：`LOGIN_FAILED | 原因短语`
- 注册成功：`REGISTER_SUCCESS | 用户ID | 用户名 | 大学 | 头像base64`
- 注册失败：`REGISTER_FAILED | 原因短语`
- 用户信息：`USER_INFO | 用户ID | 用户名 | 大学 | 头像base64`
- 大学列表：`UNIV_LIST | 大学1 | 大学2 | ...`
- 关注列表：`FOLW_LIST | 用户1 ID | 用户2 ID | ...`
- 粉丝列表：`FANS_LIST | 用户1 ID | 用户2 ID | ...`
- 发贴列表：`POST_LIST | 帖子1 ID | 帖子2 ID | ...`
- 修改成功：`EDIT_SUCCESS`
- 修改失败：`EDIT_FAILED | 原因短语`
- 未知命令：`UNKNOWN`