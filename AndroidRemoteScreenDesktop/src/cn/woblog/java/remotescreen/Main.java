package cn.woblog.java.remotescreen;

import com.google.gson.Gson;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        
        connectDevices();
//        try {
//            Socket client = new Socket("192.168.1.103", 45679);
//            PrintWriter printWriter = new PrintWriter(client.getOutputStream());
//            System.out.println("连接已建立...");
//
//            //发送消息
//            printWriter.println("hello Server");
//
//            printWriter.flush();
//
//            //InputStreamReader是低层和高层串流之间的桥梁
//            //client.getInputStream()从Socket取得输入串流
//            InputStream inputStream = client.getInputStream();
////            InputStreamReader streamReader = new InputStreamReader(inputStream);
//
//            //链接数据串流，建立BufferedReader来读取，将BufferReader链接到InputStreamReder
////            BufferedReader reader = new BufferedReader(streamReader);
////            String advice = reader.readLine();
//
//            byte[] bytes = new byte[1024];
//            int len = 0;
//            while ((len=inputStream.read(bytes))!=-1) {
//                System.out.println(Arrays.toString(bytes));
//            }
//
////            System.out.println("接收到服务器的消息 ：" + advice);
////            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void connectDevices() {
        try {
            Socket client = new Socket("192.168.1.103", 45679);

            InputStream inputStream = client.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len=inputStream.read(bytes))!=-1) {
                byteArrayOutputStream.write(bytes,0,len);
//                System.out.println(Arrays.toString(bytes));
            }

            String s = byteArrayOutputStream.toString();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
