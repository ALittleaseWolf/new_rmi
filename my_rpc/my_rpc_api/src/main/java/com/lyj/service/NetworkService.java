package com.lyj.service;

import org.opencv.core.Mat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NetworkService extends Remote {
    String trainNetwork(String datasets) throws RemoteException;
    String runNetwork(Mat img) throws RemoteException;
}
