# 命令行参数说明

## 服务端

以下是桌面服务端支持的命令行参数：

- `-h 或 --help` 显示帮助信息
- `-v 或 --version` 显示版本信息
- `-D 或 --DEBUG`  开启调试模式
- `-l 或 --log <log directory>` 设置存放日志的文件夹路径
- `-u 或 --url <url>`设置数据库连接地址，未设定则默认为`jdbc:mysql://localhost:3306/uniwood?<...args>`
- `-n 或 --username <name>` 设置数据库用户名，未设定则默认为`uniwood_user`
- `-p 或 --password <password>` 设置数据库密码，未设定则默认为`henu`
- `-P 或 --port <port>` 设置供客户端连接到的服务器端口，未设定则默认为`10475`
- `-M 或 --max-conn <max connection>` 设置最大连接数，未设定则默认为`16`

例如，要将日志文件保存到 "dat/logs" 文件夹中，并指定用户名`root`和密码`password`连接数据库，可以使用以下命令启动服务端：
`java -jar xxx.jar -l "dat/logs" -n root -p password`

## 客户端

以下是桌面客户端支持的命令行参数：

- `-D 或 --DEBUG`  开启调试模式
- `-l 或 --log <log directory>` 设置存放日志的文件夹路径
- `-H 或 --host <host>` 设置服务器地址，未设定则默认为`zpss.info`(调试模式下为`localhost`)
- `-P 或 --port <port>` 设置服务器端口，未设定则默认为`10475`
- `-t 或 --timeout <timeout mills>` 设置连接超时时间，单位为毫秒，未设定则默认为`3000`
- `-r 或 --retry <retry>` 设置重试次数，未设定则默认为`3`
- `-w 或 --wait <base waitmills>` 设置请求消息周期等待时间，单位为毫秒，未设定则默认为`10`
- `-c 或 --count <count>` 设置请求消息最大等待周期，未设定则默认为`100`

例如，要将日志文件保存到 "dat/logs" 文件夹中，可以使用以下命令启动服务端：
`java -jar xxx.jar -l "dat/logs"`