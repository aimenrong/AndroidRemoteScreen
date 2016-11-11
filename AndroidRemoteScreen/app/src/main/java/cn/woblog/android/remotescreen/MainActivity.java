package cn.woblog.android.remotescreen;

import android.app.Activity;
import android.os.Bundle;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class MainActivity extends Activity {
    private static final int DEFAULT_SOCKET_PORT = 45678;
    private static final int DEFAULT_SOCKET_PORT_STREAM = 45679;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncServer asyncServer = new AsyncServer();

        AsyncHttpServer server = new AsyncHttpServer();


        server.get("/hi", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                response.send("Hello!!!");
            }
        });
        server.listen(asyncServer, DEFAULT_SOCKET_PORT);

        AsyncHttpServer streamAsyncHttpServer = new AsyncHttpServer();
        streamAsyncHttpServer.websocket("/", new AsyncHttpServer.WebSocketRequestCallback() {
            @Override
            public void onConnected(WebSocket webSocket, AsyncHttpServerRequest request) {
                webSocket.send("a");
                webSocket.send("\r\n");
                webSocket.close();
            }
        });

        streamAsyncHttpServer.listen(asyncServer, DEFAULT_SOCKET_PORT_STREAM);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ServerSocket serverSocket = new ServerSocket(45678);
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

    private void processRequest(final Socket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 读取客户端数据
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String clientInputStr = input.readLine();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                    // 处理客户端数据
                    System.out.println("客户端发过来的内容:" + clientInputStr);

                    // 向客户端回复信息
                    PrintStream out = new PrintStream(socket.getOutputStream());
                    // 发送键盘输入的一行

                    out.println("ha");

                    out.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
