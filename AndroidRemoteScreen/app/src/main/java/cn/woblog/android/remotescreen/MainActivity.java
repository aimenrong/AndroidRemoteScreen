package cn.woblog.android.remotescreen;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

import cn.woblog.android.remotescreen.domain.AppInfo;
import cn.woblog.android.remotescreen.util.JsonUtil;
import cn.woblog.android.remotescreen.util.PacketUtil;

public class MainActivity extends Activity {
    private static final int DEFAULT_SOCKET_PORT = 45678;
    private static final int DEFAULT_SOCKET_PORT_STREAM = 45679;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        String sourceDir = PacketUtil.getSourceDir(this);
//        MyLog.d(sourceDir);

        requestStartServer();

//        AsyncServer asyncServer = new AsyncServer();
//
//        AsyncHttpServer server = new AsyncHttpServer();
//
//
//        server.get("/hi", new HttpServerRequestCallback() {
//            @Override
//            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//                response.send("Hello!!!");
//            }
//        });
//        server.listen(asyncServer, DEFAULT_SOCKET_PORT);
//        AsyncHttpServer streamAsyncHttpServer = new AsyncHttpServer();
//        streamAsyncHttpServer.websocket("/", new AsyncHttpServer.WebSocketRequestCallback() {
//            @Override
//            public void onConnected(WebSocket webSocket, AsyncHttpServerRequest request) {
//                webSocket.send("a");
//                webSocket.send("\r\n");
//                webSocket.close();
//            }
//        });
//
//        streamAsyncHttpServer.listen(asyncServer, DEFAULT_SOCKET_PORT_STREAM);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ServerSocket serverSocket = new ServerSocket(DEFAULT_SOCKET_PORT_STREAM);
//                    while (true) {
//                        //从请求队列中取出一个连接
//                        Socket client = serverSocket.accept();
//                        System.out.println("连接来了");
//                        // 处理这次连接
//                        processRequest(client);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    /**
     * 创建一个socket监听设备请求，将软件路径和，命令的类名发过去，服务器好通过adb开启服务
     */
    private void requestStartServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String sourceDir = PacketUtil.getSourceDir(MainActivity.this);
                    AppInfo appInfo = new AppInfo(sourceDir);
                    String s = JsonUtil.toJson(appInfo);

                    ServerSocket serverSocket = new ServerSocket(DEFAULT_SOCKET_PORT_STREAM);
                    Socket client = serverSocket.accept();
                    OutputStream outputStream = client.getOutputStream();

//                    PrintStream out = new PrintStream(outputStream);
                    outputStream.write(s.getBytes(Charset.defaultCharset()));

                    outputStream.close();

//                    // 读取客户端数据
//                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    String clientInputStr = input.readLine();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
//                    // 处理客户端数据
//                    System.out.println("客户端发过来的内容:" + clientInputStr);
//
//                    // 向客户端回复信息
//                    OutputStream outputStream = socket.getOutputStream();
////                    PrintStream out = new PrintStream(outputStream);
////                     发送键盘输入的一行
//
////                    out.println("ha");
//
////                    out.close();
//                    Random random = new Random();
//                    byte[] bytes = new byte[1024];
//
//                    while (true) {
//                        random.nextBytes(bytes);
//                        outputStream.write(bytes);
//                    }
////                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

//    private void processRequest(final Socket socket) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    // 读取客户端数据
//                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    String clientInputStr = input.readLine();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
//                    // 处理客户端数据
//                    System.out.println("客户端发过来的内容:" + clientInputStr);
//
//                    // 向客户端回复信息
//                    OutputStream outputStream = socket.getOutputStream();
////                    PrintStream out = new PrintStream(outputStream);
////                     发送键盘输入的一行
//
////                    out.println("ha");
//
////                    out.close();
//                    Random random = new Random();
//                    byte[] bytes = new byte[1024];
//
//                    while (true) {
//                        random.nextBytes(bytes);
//                        outputStream.write(bytes);
//                    }
////                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
}
