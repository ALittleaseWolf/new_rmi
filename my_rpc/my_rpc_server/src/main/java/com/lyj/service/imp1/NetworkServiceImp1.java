package com.lyj.service.imp1;

import com.lyj.service.NetworkService;
import org.opencv.core.Mat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NetworkServiceImp1 extends UnicastRemoteObject implements NetworkService {
    public NetworkServiceImp1() throws RemoteException{
        super();
    }
    @Override
    public String trainNetwork(String datasets) throws RemoteException {
        System.out.println("数据路径:" + datasets);
        return datasets;
    }

    @Override
    public String runNetwork(Mat img) throws RemoteException {
        /* run forward */
//        String result = forward(Mat);
        return null;
    }
}
