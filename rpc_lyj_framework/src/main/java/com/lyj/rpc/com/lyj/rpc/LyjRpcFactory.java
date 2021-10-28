package com.lyj.rpc.com.lyj.rpc;

import com.lyj.rpc.connection.ZkConnection;
import com.lyj.rpc.registry.LYJRpcRegistry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.Properties;

// 框架入口
public class LyjRpcFactory {
    // 配置信息
    private static final Properties config = new Properties();
    // 连接对象
    private static final ZkConnection connection;
    private static final LYJRpcRegistry registry;

    // 读取初始化的配置对象
    private static final Properties services = new Properties();
    /**
     * 初始化过程
     * 固定逻辑，classpath下提供配置文件, 命名为lyj-rpc.properties
     * registry.ip=localhost
     * registry.port=9090
     * zk.server=Zookeeper访问地址 localhost:2181
     * zk.sessionTimeout=ZooKeeper超时时间 10000
     */
    static {
        try {
            InputStream input = LyjRpcFactory.class.getClassLoader().getResourceAsStream("lyj.rpc.properties");

            config.load(input);
            // 服务端ip,port zk ip:port timeout
            String serverIP = config.getProperty("registry.ip") == null ? "localhost": config.getProperty("registry.ip");
            int serverPort = config.getProperty("registry.port") == null ? 9090: Integer.parseInt(config.getProperty("registry.port"));
            String zkServer = config.getProperty("zk.server") == null ? "localhost:2181":config.getProperty("zk.server");
            int zkSessionTimeout = config.getProperty("zk.sessionTimeout") == null ? 10000: Integer.parseInt(config.getProperty("zk.sessionTimeout"));
            connection = new ZkConnection(zkServer,zkSessionTimeout);
            registry = new LYJRpcRegistry();
            registry.setIp(serverIP);
            registry.setPort(serverPort);
            registry.setConnection(connection);
            // 创建一个rmi到注册器
            LocateRegistry.createRegistry(serverPort);

            List<String> children = connection.getConnection().getChildren("/", false);

            if (!children.contains("lyj")){
                connection.getConnection().create("/lyj", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            List<String> lyjChildren = connection.getConnection().getChildren("/lyj", false);
            if (!lyjChildren.contains("rpc")){
                connection.getConnection().create("/lyj/rpc", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            // 判断classpath下是否有配置文件lyj.service.properties
            // 接口=实现类全命名
            InputStream servicesInput = LyjRpcFactory.class.getClassLoader().getResourceAsStream("lyj.service.properties");
            if(servicesInput != null){
                services.load(servicesInput);
                for(Object key: services.keySet()){
                    Object value = services.get(key);
                    Class<Remote> serviceInterface = (Class<Remote>) Class.forName(key.toString());
                    Remote serviceObject = (Remote) Class.forName(value.toString()).newInstance();
                    registry.registerService(serviceInterface, serviceObject);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void registryService(Class<? extends Remote> serviceInterface, Remote serviceObject) throws IOException, InterruptedException, KeeperException {
        registry.registerService(serviceInterface, serviceObject);
    }

    public static <T extends Remote>T getServiceProxy(Class<T> serviceInterface) throws IOException, InterruptedException, KeeperException, NotBoundException {
        return registry.getServiceProxy(serviceInterface);
    }
}
