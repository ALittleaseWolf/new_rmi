package com.lyj.rpc.registry;

import com.lyj.rpc.connection.ZkConnection;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.List;

//注册器工具
//通过zk连接对象，和传入的remote接口实现对象，完成RMI地址的拼接
//缺少LocateRegistry
public class LYJRpcRegistry {
    // 连接对象
    private ZkConnection connection;
    private String ip;
    private int port;

    /*
    问题：反复注册抛出异常KeeperErrorCode = NodeExists for /lyj/rpc/com.lyj.service.UserService
    原因：节点已存在
    解决办法：先查询节点，存在则删除
    注册服务的方法
    @param  serviceInterface 服务接口类对象 com.lyj.service.UserService.class
    @param serviceObject 服务实现类型的对象 new com.lyj.service.impl.UserServiceImpl1
     */
    public void registerService(Class<? extends Remote> serviceInterface, Remote serviceObject) throws IOException, InterruptedException, KeeperException {
        String rmi = "rmi://"+ip+":"+port+"/"+serviceInterface.getName();

        String path = "/lyj/rpc/"+serviceInterface.getName();

        List<String> children = connection.getConnection().getChildren("/lyj/rpc",false);
        if(children.contains(serviceInterface.getName())) {
            Stat stat = new Stat();
            connection.getConnection().getData(path,false,stat);
            connection.getConnection().delete(path, stat.getCversion());
        }

        connection.getConnection().create(path, rmi.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //服务对象注册到rmi到registry
        Naming.rebind(rmi, serviceObject);
    }

    public <T extends Remote>T getServiceProxy(Class<T> serviceInterface) throws IOException, InterruptedException, KeeperException, NotBoundException {
        String path = "/lyj/rpc/"+serviceInterface.getName();
        byte[] datas = connection.getConnection().getData(path,false,null);
        String rmi = new String(datas);

        Object obj = Naming.lookup(rmi);
        return (T)obj;
    }

    public ZkConnection getConnection(){
        return connection;
    }

    public void setConnection(ZkConnection connection){
        this.connection = connection;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
