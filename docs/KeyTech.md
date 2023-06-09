# 关键技术

## 一、综合

### 1. 协议消息串类

- 解决的问题：
	- 手动进行协议消息串与数据的转换代码冗余，拼接字符串过于繁琐，可读性差
	- 命令参数和字面量字符串混杂，不易维护
	- 采用PrintWriter进行Socket通信时，需要对换行符手动进行处理
- 实现方法：
	- 设计协议消息串工具类MsgProto，将协议消息串与数据的转换封装在类中，提供了一系列静态方法及`toString()`方法的重写，可直接调用
	- 设计命令参数枚举类Command，将命令参数和字面量字符串分离，提高了代码可读性和可维护性
	- 在MsgProto类中提供换行符'\n'与字符串"\n"相关互转方法，与网络通信类相关类配合，方便控制换行符的传输与显示

### 2. 控制台参数解析接口

- 解决的问题：
	- 程序在不同时期和不同环境的条件下运行时所需的参数不同，需要在程序启动时通过命令行参数进行配置
	- 服务端为控制台程序，不能通过GUI界面进行配置，需要通过命令行参数进行配置
- 实现方法：
	- 设计Arguable接口，提供`config(String[] args)`方法的抽象，供有需要的类继承实现通过main函数参数进行配置
	- Arguable接口中有通过传入main函数参数，从而获取命令行参数的静态方法，供有需要的类调用

### 3. 日志系统

- 解决的问题：
	- 编写网络应用时，收发消息过程中需要记录日志，便于调试和问题定位
	- 程序运行时日志需要需要按照时间顺序记录线程、消息内容等信息，便于维护
	- 日志信息需要输出到控制台和日志文件，日志文件需要按照日期分割，便于查找
- 实现方法：
	- 设计Logger日志抽象类，提供日志消息记录方法的实现
	- Logger类中有通过命令行参配置日志输出的抽象方法（实现了Arguable接口），供客户端和服务端继承实现

---

## 二、服务端

### 1. 数据库连接

- 解决的问题：
	- 客户端数据不能依赖于服务端内存的运行时内容，需实现相关数据的持久化
	- 客户端采用多线程并发访问服务端，通过文件系统实现数据资源的并发访问效率太低
- 实现方法：
	- 在编写服务端前先设计数据库结构，随后通过数据库表的结构设计实体类及数据库访问类
	- 服务端启动时首先连接数据库，之后再通过客户端的请求进行数据库的增删改查操作

### 2. 代码分层设计

- 解决的问题：
	- 数据实体、数据库访问、业务逻辑、网络通信等代码混杂，耦合性过高
	- 服务端代码缺乏层次性，不易阅读和维护
- 实现方法：
	- 设计服务端代码分层，根据功能分为`entity`层、`dao`层、`service`层、`util`层
		- entity层：存放数据实体类，与数据库表结构一一对应，仅提供属性的get、set方法，供dao层调用
		- dao层：单例模式，存放数据库访问类接口及其实现，提供对应数据实体关于数据库的增删改查操作，供service层调用
		- service层：单例模式，存放业务逻辑类接口及其实现，提供对应业务逻辑的方法，供其他类调用
		- util层：存放工具类，包含数据库连接类、服务端日志类实现及网络通信相关类

---

## 三、客户端

### 1. 界面-设计

- 解决的问题：
	- 客户端需要提供用户界面，便于用户进行操作，这就要求客户端图形界面需要基本满足跨平台和响应式的需求
	- 客户端需要提供分区、贴子、楼层等多种样式的列表，需要提供多种样式的自定义列表项界面
- 实现方法：
	- 采用Java Swing相关框架及其中的布局管理器进行界面设计，提供基础的跨平台响应式用户界面体验
		- 对大小可变的窗口组件，采用相对布局如`BorderLayout`、`FlowLayout`等定位控件，实现界面的响应式
		- 对大小不可变的窗口组件，将布局管理器设置为`null`，通过计算设置控件的位置和大小，确保窗口正常显示
	- 分区列表采用`JList`，通过自定义`ListCellRenderer`实现自定义的列表项显示效果
	- 贴子和楼层列表控件需含有供点击的按钮和文本，通过`JList`无法满足需求；通过自定义Render类模拟`JList`的效果，实现自定义的列表项显示效果
	- 在必要位置重写窗口的`repaint`方法，实现界面的响应式和实时刷新

### 2. 界面-控制

- 解决的问题：
	- 客户端界面需要与服务端进行交互，需要设计界面与业务逻辑的分离
	- 客户端界面需要实现跨界面的数据传递
- 实现方法：
	- 采用MVC设计模式进行代码分层，实现界面与业务逻辑分离
		- model层：存放数据实体类，包含对应界面所需全部资源，供controller层调用
		- view层：存放界面类接口及其实现，采用Java Swing相关框架进行界面设计，仅提供界面的显示、隐藏和部分控件获取方法，供controller层调用
		- controller层：单例模式，存放界面控制器类，提供对应界面的业务逻辑方法，供其他类调用，实现跨界面的数据传递

### 3. 网络连接-心跳机制

- 解决的问题：
	- 客户端与服务端通过网络进行通信，网络通信过程中可能出现网络异常，导致客户端与服务端的连接中断
	- 客户端与服务端的连接中断后，客户端需要重新连接服务端，若重新连接成功，客户端需要恢复用户登录状态
- 实现方法：
	- 客户端与服务端建立连接后，客户端每隔一段时间向服务端发送心跳包，服务端收到心跳包后，将客户端状态更新为在线
	- 用户登录成功后，客户端在心跳包中携带用户信息，服务端收到心跳包后，将用户状态更新为在线
	- 若服务端在一段时间内未收到客户端的心跳包，服务端认为与客户端的连接中断，将关闭与该客户端的连接连接，并将用户状态更新为离线

### 4. 网络连接-递归异步等待

- 解决的问题：
	- 客户端向服务端请求特定资源，服务端可能需要一段时间才能返回结果
	- 客户端需要等待服务端的响应，不能在请求后立即更新界面
	- 若采用线程间通信或阻塞等待的方式，会导致客户端界面卡顿，且代码复杂度较高
- 实现方法：
	- 采用递归异步等待的方式编写相关函数，实现客户端的等待
		1. 在获取相应资源前，将待获取资源设置为`null`，等待服务端返回结果
		2. 客户端在与通信线程相异的线程执行相应操作对应递归异步等待函数，向服务端发送相关请求
		3. 函数停止等待一段时间后，递归调用自身，判断待获取资源是否为`null`
		4. 直到函数获取到服务端返回的结果后，返回相应资源，令客户端更新界面
		5. 若函数等待时间超过一定时间，或递归调用次数超过一定次数仍未获取到服务端返回的结果，结束递归并提示网络异常

### 5. 网络连接-动态等待时间

- 解决的问题：
	- 客户端向服务端请求特定资源，服务端可能需要一段时间才能返回结果，且服务端返回结果的时间不确定
	- 采用递归异步等待的方式，需要设置等待时间，若设置的等待时间过短，会导致递归次数过多；若设置的等待时间过长，会导致客户端界面卡顿
- 实现方法：
	- 编写使用`WaitTime`工具类，提供`getMaxWaitCycle()`和`getWaitCycleMills()`方法，供递归异步等待函数调用
		- `getMaxWaitCycle()`方法返回最大等待次数
		- `getWaitCycleMills(int waitCount)`方法返回根据平均等待次数计算的等待时间，且会根据递归次数计算所有调用的平均等待次数
		- `WaitTime`类中定义了一个定时器，定时更新平均等待次数，以此实现动态等待时间

### 6. 用户头像处理

- 解决的问题：
	- 用户设置的头像文件格式、大小不确定，需要将用户头像转换或压缩为标准格式大小
	- 客户端需要将用户头像存储在服务器，若采用文件形式存储，不仅代码量更大，也会加大服务器的存储压力和网络传输压力
	- 数据库中不支持直接存储图片，需要将图片转换为字符串形式存储
	- 客户端需要将用户头像转换为`ImageIcon`对象，供界面显示
- 实现方法：
	- 编写使用`Avatar`工具类，提供`fromBase64()`、`fromFile()`、`toBase64()`和`toIcon(int length)`方法
	- 客户端在用户设置头像时，调用`fromFile()`方法设置一个Avatar对象，自动完成头像的压缩
	- 客户端在向服务端发送用户头像时，调用`toBase64()`方法将头像转换为base64的字符串形式，通过协议消息串包装发送至服务端
	- 服务端收到包含头像base64编码的协议消息串后，将其按文本形式原样存储在数据库中
	- 客户端在从服务端获取用户头像的base64后时，调用`fromBase64()`和`toIcon(int length)`方法获取ImageIcon对象，供界面显示

### 7. 数据缓存层

- 解决的问题：
	- 客户端需要频繁向服务端请求数据，且不同次请求的数据可能相同；若每次请求都向服务端发送请求，会导致网络传输压力过大和客户端界面卡顿
	- 由服务端发回的数据需要存储在可供递归异步等待函数访问到的数据结构中，以供判断是否获取到服务端返回的结果
- 实现方法：
	- 编写使用实体类`entity`层对应`builder`层，采用单例模式，存储客户端需要缓存的数据
	- `builder`层实现`Builder<E extends Entity>`接口，提供`hold()`、`add()`、`build(Integer id)`方法
		- `hold()`方法实现定时执行的定时器，定时清空缓存即实体类对象Map
		- `add()`方法将实体类对象添加到缓存即实体类对象Map中，供网络连接类在服务端返回结果后更新
		- `build(Integer id)`方法若缓存中存在对应对象id则返回缓存中的实体类对象，否则向服务端发送请求，异步等待服务端返回结果