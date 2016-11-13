package cn.woblog.java.remotescreen;

import cn.woblog.java.remotescreen.domain.AppInfo;
import cn.woblog.java.remotescreen.ui.MainFrame;
import cn.woblog.java.remotescreen.util.JsonUtil;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static String[] mArgs;
    private MainFrame mMainFrame;

    public static void main(String[] args) {
        mArgs = args;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().initialize();
            }
        });


//        connectDevices();

//        new MainFrame();



//        runCommand("adb shell export CLASSPATH=/data/app/cn.woblog.android.remotescreen-1/base.apk");
//        runCommand("adb shell exec app_process /system/bin cn.woblog.android.remotescreen.Main");
//runCommand("exec ls /data/app/cn.woblog.android.remotescreen-1/base.apk");
//        runCommand("adb shell app_process /system/bin cn.woblog.android.remotescreen.Main");

//        sh -c "CLASSPATH=' + t + " " + s + " /system/bin " + n + '"

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
            Socket client = new Socket("192.168.1.100", 45679);

            InputStream inputStream = client.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, len);
//                System.out.println(Arrays.toString(bytes));
            }

            String s = byteArrayOutputStream.toString();
            System.out.println(s);


            AppInfo appInfo = JsonUtil.toObject(s, AppInfo.class);

            startClientServer(appInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startClientServer(AppInfo appInfo) {
        //调用adb 执行
//            String  command= "export CLASSPATH="+appInfo.getSourceDir();
//            String command1 = "exec app_process /system/bin cn.woblog.android.remotescreen.Main";

        String c = "adb shell sh -c \"CLASSPATH=" + appInfo.getSourceDir() + " app_process /system/bin " + appInfo.getMainClassName() + "\"";

        new Thread(new Runnable() {
            @Override
            public void run() {
                runCommand(c);
            }
        }).start();

        //连接视频流
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        try {
            System.out.println("wait video");
            Thread.sleep(1000);

            Socket client = new Socket("192.168.1.100", 45681);

            InputStream inputStream = client.getInputStream();

            System.out.println("connect video success");

            while (true) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                int c1 = 0;
                int c2 = 0;
                while (c2 != -1 && !(c1 == '\r' && c2 == '\n')) {
                    c1 = c2;
                    c2 = inputStream.read();
                    byteArrayOutputStream.write(c2);
                }

                System.out.println(Arrays.toString(byteArrayOutputStream.toByteArray()));
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//            }
//        }).start();

    }

    private static void runCommand(String c) {
        try {
            System.out.println(c);
            Process process = Runtime.getRuntime().exec(c);

            List<String> processList = new ArrayList<String>();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
//
//            processList.forEach(e-> System.out.println(e));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        mMainFrame = new MainFrame(mArgs);
        mMainFrame.setLocationRelativeTo(null);
        mMainFrame.setVisible(true);
        mMainFrame.setFocusable(true);
        mMainFrame.selectDevice();
    }

}
