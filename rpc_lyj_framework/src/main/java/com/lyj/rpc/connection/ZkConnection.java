package com.lyj.rpc.connection;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZkConnection {
    // zk地址
    private String zkServer;
    // 超时时间
    private int sessionTimeout;
    public ZkConnection(){
        super();
        this.zkServer="localhost:2181";
        this.sessionTimeout=10000;
    }
    public ZkConnection(String zkServer, int sessionTimeout){
        this.zkServer=zkServer;
        this.sessionTimeout=sessionTimeout;
    }
    public ZooKeeper getConnection() throws IOException {
        return new ZooKeeper(zkServer, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent){
            }
        });
    }
}
