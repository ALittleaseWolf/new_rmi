package com.lyj;

import com.lyj.rpc.com.lyj.rpc.LyjRpcFactory;
import com.lyj.service.UserService;
import com.lyj.service.imp1.UserServiceImp1;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.rmi.RemoteException;

public class ServerApplication {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException, ClassNotFoundException {
//        UserService userService = new UserServiceImp1();
//        LyjRpcFactory.registryService(UserService.class, userService);
        Class.forName("com.lyj.rpc.com.lyj.rpc.LyjRpcFactory");
    }
}
