package com.lyj.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

// 服务接口
public interface UserService extends Remote {
    // 根据用户名查询用户，返回json格式到字符串，描述用户对象
    String getUser(String name) throws RemoteException;

}
