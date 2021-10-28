package com.lyj;

import com.lyj.rpc.com.lyj.rpc.LyjRpcFactory;
import com.lyj.service.CustomerService;
import com.lyj.service.NetworkService;
import com.lyj.service.UserService;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.rmi.NotBoundException;

public class TestRpcClient {
    public static void main(String[] args){
        try {
//            UserService userService = LyjRpcFactory.getServiceProxy(UserService.class);
//            System.out.println(userService.getClass().getName());
//            String result = userService.getUser("管理员");
//            System.out.println("远程服务返回查询结果："+result);
            NetworkService networkService = LyjRpcFactory.getServiceProxy(NetworkService.class);
            System.out.println(networkService.getClass().getName());
            String result = networkService.trainNetwork("girls");
            System.out.println("返回结果:"+ result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
