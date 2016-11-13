package cn.woblog.android.remotescreen;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.IWindowManager;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by renpingqing on 16/11/10.
 */

public class Main {
    //    export CLASSPATH=/data/app/cn.woblog.android.remotescreen-1.apk
    //    export CLASSPATH=/data/app/cn.woblog.android.remotescreen-1/base.apk
//    exec app_process /system/bin cn.woblog.android.remotescreen.Main

    public static final String TAG = "TAG";
    private static final int DEFAULT_SOCKET_PORT = 45680;
    private static final int DEFAULT_SOCKET_PORT_STREAM = 45681;
    private static IWindowManager iWindowManager;
    private static Looper looper;

    public static void main(String... args) {

        Log.d(TAG, "run");
        System.out.println("run");

        try {
            Looper.prepare();
            looper = Looper.myLooper();
            AsyncServer server = new AsyncServer();
            AsyncHttpServer httpServer = new AsyncHttpServer() {

                protected boolean onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                    Log.i(TAG, request.getHeaders().toString());
                    return super.onRequest(request, response);
                }
            };
            Class cls = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = cls.getDeclaredMethod("getService", new Class[]{String.class});
            final IWindowManager wm = IWindowManager.Stub.asInterface((IBinder) getServiceMethod.invoke(null, new Object[]{"window"}));
            httpServer.get("/screenshot.jpg", new AnonymousClass5(wm));
            Log.i(TAG, "Server starting");
            if (httpServer.listen(server, DEFAULT_SOCKET_PORT) == null /*|| rawSocket == null */) {
                System.out.println("No server socket?");
                Log.e(TAG, "No server socket?");
                throw new AssertionError("No server socket?");
            }
            System.out.println("Started");

//            registerVideoStream()

            ///////////
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            ServerSocket serverSocket = new ServerSocket(DEFAULT_SOCKET_PORT_STREAM);
                            Socket client = serverSocket.accept();
                            processRequestHandler(wm, client);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }


            }).start();
///////////////////

            Log.i(TAG, "Waiting for exit");
            Looper.loop();
            Log.i(TAG, "Looper done");
            server.stop();
            Log.i(TAG, "Done!");
            System.exit(0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//

//        try {
//            Looper.prepare();
//            looper = Looper.myLooper();
//
//            AsyncServer asyncServer = new AsyncServer();
//            final AsyncHttpServer server = new AsyncHttpServer();
//            Class cls = Class.forName("android.os.ServiceManager");
//            Method getServiceMethod = cls.getDeclaredMethod("getService", new Class[]{String.class});
//            IWindowManager wm = IWindowManager.Stub.asInterface((IBinder) getServiceMethod.invoke(null, new Object[]{"window"}));
//
//            //screenshot
//            server.get("/screenshot.jpg", new AnonymousClass5(wm));
//
////            //video
////            server.get("/h264", new HttpServerRequestCallback() {
////                public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//////                    Log.i("VysorMain", "New http connection accepted.");
//////                    if(!Main.checkPassword(request.getQuery().getString("password"))) {
//////                        Log.i("VysorMain", "h264 authentication failed");
//////                        response.code(401);
//////                        response.send("Not Authorized.");
//////                        return;
//////                    }
//////
//////                    Log.i("VysorMain", "h264 authentication success");
////                    try {
////                        Main.turnScreenOn(this.val$im, this.val$injectInputEventMethod, this.val$pm);
////                    }
////                    catch(Exception v1) {
////                        v1.printStackTrace();
////                    }
////
////                    response.getHeaders().set("Access-Control-Allow-Origin", "*");
////                    response.getHeaders().set("Connection", "close");
////                    response.setClosedCallback(new CompletedCallback() {
////                        public void onCompleted(Exception ex) {
////                            Log.i("VysorMain", "Connection terminated.");
////                            if(ex != null) {
////                                Log.e("VysorMain", "Error", ((Throwable)ex));
////                            }
////
////                            server.stop();
////                        }
////                    });
////                }
////            });
//
//            server.listen(asyncServer, DEFAULT_SOCKET_PORT);
//
//            System.out.println("Started");
//            Log.i(TAG, "Waiting for exit");
//
//            Looper.loop(); //一定要写
////            Log.i(TAG, "Looper done");
////            server.stop();
////            Log.i(TAG, "Done!");
////            System.exit(0);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }


    }

    private static void processRequestHandler(IWindowManager wm, Socket client) {
        boolean isRun = true;

        OutputStream outputStream = null;
        ByteArrayOutputStream bout = null;
        try {
            outputStream = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (isRun) {
            try {

                Bitmap bitmap = EncoderFeeder.screenshot(wm);
                bout = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bout);
//                bout.write("a2343454565".getBytes());

                outputStream.write(bout.toByteArray());
                outputStream.write("\r\n".getBytes());
                outputStream.flush();

            } catch (Exception e) {
                e.printStackTrace();
                isRun = false;
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    bout.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                Log.d(TAG, "close");
//            response.code(500);
//            response.send(e.toString());
            }
        }

    }

    private static void registerVideoStream() {

    }

//    private static void init() {
//        try {
//            Class cls = Class.forName("android.os.ServiceManager");
//            Method getServiceMethod = cls.getDeclaredMethod("getService",
//                    new Class[]{String.class});
//            IBinder invoke = (IBinder) getServiceMethod.invoke(null,
//                    new Object[]{"window"});
//            iWindowManager = IWindowManager.Stub
//                    .asInterface(invoke);
//
//            if (invoke == null) {
//                System.out.println("invoke null");
//                return;
//            }
//
//            if (iWindowManager == null) {
//                System.out.println("iWindowManager null");
//                return;
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }

    public static Bitmap screenshot(IWindowManager wm, Point point)
            throws Exception {
        String surfaceClassName;
        if (Build.VERSION.SDK_INT <= 17) {
            surfaceClassName = "android.view.Surface";
        } else {
            surfaceClassName = "android.view.SurfaceControl";
        }
        Bitmap b = (Bitmap) Class
                .forName(surfaceClassName)
                .getDeclaredMethod("screenshot",
                        new Class[]{Integer.TYPE, Integer.TYPE})
                .invoke(null,
                        new Object[]{Integer.valueOf(point.x),
                                Integer.valueOf(point.y)});
        // int rotation = wm.getRotation();
        // if (rotation == 0) {
        // return b;
        // }
        // Matrix m = new Matrix();
        // if (rotation == 1) {
        // m.postRotate(-90.0f);
        // } else if (rotation == 2) {
        // m.postRotate(-180.0f);
        // } else if (rotation == 3) {
        // m.postRotate(-270.0f);
        // }
        // return Bitmap.createBitmap(b, 0, 0, size.x, size.y, m, false);

        return b;
    }


}
