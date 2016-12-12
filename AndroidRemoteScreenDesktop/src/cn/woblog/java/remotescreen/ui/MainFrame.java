package cn.woblog.java.remotescreen.ui;

import cn.woblog.java.remotescreen.domain.AppInfo;
import cn.woblog.java.remotescreen.util.ByteUtil;
import cn.woblog.java.remotescreen.util.CommandUtil;
import cn.woblog.java.remotescreen.util.JsonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by renpingqing on 11/13/16.
 */
public class MainFrame extends JFrame {

    private static final String HOST = "localhost";
    private MonitorThread mMonitorThread;
    private MainPanel mPanel;
    private InputStream inputStream;
    private Socket client;
    private BufferedReader br;
//    private DataInputStream dataInputStream;

    public MainFrame(String[] mArgs) {
//        this.setSize(400, 300);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        label = new JLabel();
//        label.setHorizontalAlignment(JLabel.CENTER);
//        this.setLayout(new BorderLayout());
//
//        this.add(label,BorderLayout.CENTER);
//
//        label.setIcon(new ImageIcon("http://www.imooc.com/static/img/index/logo.png"));

        initializeFrame();
        initializePanel();
    }

    private void initializeFrame() {
        setTitle("Android Screen Monitor");
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("logo.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initializePanel() {
        mPanel = new MainPanel();
        add(mPanel);
    }


    public void selectDevice() {
        connectDevices();

        startMonitor();
    }

    public void startMonitor() {
        mMonitorThread = new MonitorThread();
        mMonitorThread.start();
    }

    public void stopMonitor() {
        mMonitorThread = null;
    }

    public void setImage(BufferedImage image) {
//        if (fbImage != null) {
//            mRawImageWidth = fbImage.getRawWidth();
//            mRawImageHeight = fbImage.getRawHeight();
//        }


        mPanel.setImage(image);
        updateSize();
    }

    public void updateSize() {
        int width = 300;
        int height = 500;
//        if (mPortrait) {
//            width = mRawImageWidth;
//            height = mRawImageHeight;
//        } else {
//            width = mRawImageHeight;
//            height = mRawImageWidth;
//        }
//        Insets insets = getInsets();
//        int newWidth = (int) (width * mZoom) + insets.left + insets.right;
//        int newHeight = (int) (height * mZoom) + insets.top + insets.bottom;
//
//        // Known bug
//        // If new window size is over physical window size, cannot update window
//        // size...
//        // FIXME
//        if ((getWidth() != newWidth) || (getHeight() != newHeight)) {
        setSize(width, height);
//        }
    }

    private void connectDevices() {
        try {
            Socket client = new Socket(HOST, 45679);

            InputStream inputStream = client.getInputStream();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, len);
            }

            String s = byteArrayOutputStream.toString();
            System.out.println(s);


            AppInfo appInfo = JsonUtil.toObject(s, AppInfo.class);

            startClientServer(appInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startClientServer(AppInfo appInfo) {
        String c = "adb shell sh -c \"CLASSPATH=" + appInfo.getSourceDir() + " app_process /system/bin " + appInfo.getMainClassName() + "\"";

        new Thread(new Runnable() {
            @Override
            public void run() {
                CommandUtil.runCommand(c);
            }
        }).start();

        //获取视频流
        try {
            System.out.println("wait video");
            Thread.sleep(1000);

            client = new Socket(HOST, 45681);

            inputStream = client.getInputStream();

            DataInputStream dis = new DataInputStream(inputStream);
            br =new BufferedReader(new InputStreamReader(dis,"utf-8"));

//            dataInputStream = new DataInputStream(inputStream);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public class MainPanel extends JPanel {
        private BufferedImage image;

        public MainPanel() {
            setBackground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                int srcWidth;
                int srcHeight;
                int dstWidth;
                int dstHeight;
//                if (mPortrait) {
//                    srcWidth = mRawImageWidth;
//                    srcHeight = mRawImageHeight;
//                } else {
//                    srcWidth = mRawImageHeight;
//                    srcHeight = mRawImageWidth;
//                }
//                dstWidth = (int) (srcWidth * mZoom);
//                dstHeight = (int) (srcHeight * mZoom);
//                if (mZoom == 1.0) {
                g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
//                    g.drawImage(mFBImage, 0, 0, dstWidth, dstHeight, 0, 0,
//                            srcWidth, srcHeight, null);
//                } else {
//                    Image image = mFBImage.getScaledInstance(dstWidth,
//                            dstHeight, Image.SCALE_SMOOTH);
//                    if (image != null) {
//                        g.drawImage(image, 0, 0, dstWidth, dstHeight, 0, 0,
//                                dstWidth, dstHeight, null);
//                    }
//                }
            }
        }

        public BufferedImage getImage() {
            return image;
        }

        public void setImage(BufferedImage image) {
            this.image = image;
            repaint();
        }
    }

    public class MonitorThread extends Thread {

        @Override
        public void run() {
//            Thread thread = Thread.currentThread();
//            if (mDevice != null) {
//                try {
//                    while (mMonitorThread == thread) {
//                        final FBImage fbImage = getDeviceImage();
//                        SwingUtilities.invokeLater(new Runnable() {
//                            public void run() {
//                                setImage(fbImage);
//                            }
//                        });
//                    }
//                } catch (IOException e) {
//                }
//            }

            try {

                while (mMonitorThread != null) {
                    sleep(500);
                    final BufferedImage image = getDeviceImage();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            setImage(image);
                        }
                    });

                }
            } catch (IOException e) {
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private BufferedImage getDeviceImage() throws IOException {
//            inputStream = client.getInputStream();
//            BufferedImage image = ImageIO.read(new File("/Users/renpingqing/Desktop/s.png"));
//            System.out.println("connect video success");
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//            int c1 = 0;
////            int c2 = 0;
//            while (c1 != -1 && !(c1 == '\r')) {
//                c1 = inputStream.read();
//                byteArrayOutputStream.write(c1);
//            }


//            byte[] bytes = new byte[4];
//            int length = inputStream.read(bytes);
//            if (length != -1) {
//                int dataLength = ByteUtil.byte2int(bytes);
//                System.out.println("read image size:" + dataLength);
////
//                inputStream.skip(dataLength);
//
////                byte[] dataByte = new byte[length];
////                inputStream.read(dataByte);
////
////                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataByte);
////
////                BufferedImage image = ImageIO.read(byteArrayInputStream);
////                return image;
//            }

            String s = br.readLine();
            byte[] bytes = s.getBytes("utf-8");
            System.out.println("read image byte:" + Arrays.toString(bytes));
            if (true) {
                int dataLength = ByteUtil.byte2int(bytes);
                System.out.println("read image size:" + dataLength);
//
                inputStream.skip(dataLength);

//                byte[] dataByte = new byte[length];
//                inputStream.read(dataByte);
//
//                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataByte);
//
//                BufferedImage image = ImageIO.read(byteArrayInputStream);
//                return image;
            }

            return null;
        }

    }
}
