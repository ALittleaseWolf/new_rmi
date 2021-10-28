package com.lyj.service.imp1;

import com.lyj.service.NetworkService;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

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
    public String runNetwork() throws RemoteException {
        /* run forward */
//        String result = forward(Mat);
        test();
        return "OK";
    }

    static {
        //必须要写
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("opencv\t"+Core.VERSION);
    }
    private static final Properties services = new Properties();
    public void test(){
        Mat source, template;
        //将文件读入为OpenCV的Mat格式
        template = Imgcodecs.imread("/Users/liaoyujian/Documents/code/java/new_rmi/my_rpc/my_rpc_server/src/main/resources/template.png");
        source = Imgcodecs.imread("/Users/liaoyujian/Documents/code/java/new_rmi/my_rpc/my_rpc_server/src/main/resources/source.png");
        System.out.println(source.rows() + "   " + template.rows());
        //创建于原图相同的大小，储存匹配度
        Mat result = Mat.zeros(source.rows() - template.rows() + 1, source.cols() - template.cols() + 1, CvType.CV_32FC1);
        //调用模板匹配方法
        Imgproc.matchTemplate(source, template, result, Imgproc.TM_SQDIFF_NORMED);
        //规格化
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
        //获得最可能点，MinMaxLocResult是其数据格式，包括了最大、最小点的位置x、y
        Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
        Point matchLoc = mlr.minLoc;
        //在原图上的对应模板可能位置画一个绿色矩形
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.width(), matchLoc.y + template.height()), new Scalar(0, 255, 0));
        //将结果输出到对应位置
        Imgcodecs.imwrite("/Users/liaoyujian/Documents/code/java/new_rmi/my_rpc/my_rpc_server/src/main/resources/result.png", source);

    }
}
