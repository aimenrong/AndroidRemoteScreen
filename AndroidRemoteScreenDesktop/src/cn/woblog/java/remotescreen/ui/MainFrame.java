package cn.woblog.java.remotescreen.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by renpingqing on 11/13/16.
 */
public class MainFrame extends JFrame {

    private MonitorThread mMonitorThread;
    private MainPanel mPanel;

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
                    final BufferedImage image = getDeviceImage();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            setImage(image);
                        }
                    });
                }
            } catch (IOException e) {
            }

        }

        private BufferedImage getDeviceImage() throws IOException {
            BufferedImage image = ImageIO.read(new File("/Users/renpingqing/Desktop/s.png"));
            return image;
        }

    }
}
