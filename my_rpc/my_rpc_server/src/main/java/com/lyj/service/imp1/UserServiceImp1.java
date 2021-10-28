package com.lyj.service.imp1;

import com.lyj.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServiceImp1 extends UnicastRemoteObject implements UserService {
    public UserServiceImp1() throws RemoteException{
        super();
    }
    @Override
    public String getUser(String name) throws RemoteException {
        System.out.println("要查询到用户是:" + name);

        return "{\"name\":\""+name+"\",\"age\":20, \"gender\":\"男\"}";
    }
}
