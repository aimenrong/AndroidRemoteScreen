package cn.woblog.java.remotescreen;

import cn.woblog.java.remotescreen.domain.AppInfo;
import cn.woblog.java.remotescreen.ui.MainFrame;
import cn.woblog.java.remotescreen.util.CommandUtil;
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


    }

    public void initialize() {
        mMainFrame = new MainFrame(mArgs);
        mMainFrame.setLocationRelativeTo(null);
        mMainFrame.setVisible(true);
        mMainFrame.setFocusable(true);
        mMainFrame.selectDevice();
    }


}
