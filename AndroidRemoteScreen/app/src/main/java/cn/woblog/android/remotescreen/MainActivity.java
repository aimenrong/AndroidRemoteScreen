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
    //    private static final int DEFAULT_SOCKET_PORT = 45678;
    private static final int DEFAULT_SOCKET_PORT_STREAM = 45679;
    private boolean isEnableListener = true;

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

    }

    /**
     * 创建一个socket监听设备请求，将软件路径和，命令的类名发过去，服务器好通过adb开启服务
     */
    private void requestStartServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isEnableListener) {
                    try {
                        ServerSocket serverSocket = new ServerSocket(DEFAULT_SOCKET_PORT_STREAM);
                        Socket client = serverSocket.accept();
                        processRequestHandler(client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }


        }).start();

    }

    private void processRequestHandler(Socket client) {
        try {
            String sourceDir = PacketUtil.getSourceDir(MainActivity.this);
            AppInfo appInfo = new AppInfo(sourceDir, "cn.woblog.android.remotescreen.Main");
            String s = JsonUtil.toJson(appInfo);
            OutputStream outputStream = client.getOutputStream();
//                    PrintStream out = new PrintStream(outputStream);
            outputStream.write(s.getBytes(Charset.defaultCharset()));

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
