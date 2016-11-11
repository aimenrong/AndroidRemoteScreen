package cn.woblog.java.remotescreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try {
            Socket client = new Socket("192.168.0.157", 45679);
            PrintWriter printWriter = new PrintWriter(client.getOutputStream());
            System.out.println("连接已建立...");

            //发送消息
            printWriter.println("hello Server");

            printWriter.flush();

            //InputStreamReader是低层和高层串流之间的桥梁
            //client.getInputStream()从Socket取得输入串流
            InputStreamReader streamReader = new InputStreamReader(client.getInputStream());

            //链接数据串流，建立BufferedReader来读取，将BufferReader链接到InputStreamReder
            BufferedReader reader = new BufferedReader(streamReader);
            String advice = reader.readLine();

            System.out.println("接收到服务器的消息 ：" + advice);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
