package com.lyj.service.imp1;

import com.lyj.service.CustomerService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CustomerServiceimp1 extends UnicastRemoteObject implements CustomerService {
    public CustomerServiceimp1()throws RemoteException {
        super();
    }
    @Override
    public String getCustomer(String name) throws RemoteException{
        System.out.println("查询客户" +name);
        return "查询客户"+name;
    }

    @Override
    public int addCustomer(String name) throws RemoteException{
        System.out.println("新增客户:" + name);
        return 1;
    }
}
