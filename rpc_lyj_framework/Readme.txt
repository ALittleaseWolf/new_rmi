服务端启动时，
    提供默认的端口号如6060
    指定zoomkeeper所在位置
    提供发布的服务对象，remote接口的实现
    rmi的uri地址，由固定的逻辑组成
        com.lyj.service.impl1.UserServiceImp1
        rmi://ip:port/UserServiceImp1
    IP由启动代码指定或者自动获取

客户端启动时
    必须指定zookeeper所在位置
    提供要创建的代理接口类型
        如com.lyj.service.UserService
    自动连接zk，拼接一个有规则的节点名称
        如/lyj/com.lyj.service.UserService
        保存的数据data，就是远程服务端地址 rmi://ip:port/UserService
    客户端使用的代理对象，由规则生成。