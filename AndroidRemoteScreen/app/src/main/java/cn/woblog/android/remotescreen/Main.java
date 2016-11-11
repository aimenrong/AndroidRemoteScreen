package cn.woblog.android.remotescreen;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.IWindowManager;

/**
 * Created by renpingqing on 16/11/10.
 */

public class Main {
    //    export CLASSPATH=/data/app/cn.woblog.android.remotescreen-1.apk
//    exec app_process /system/bin cn.woblog.android.remotescreen.Main

    public static final String TAG = "TAG";
    private static final int DEFAULT_SOCKET_PORT = 53586;
    private static IWindowManager iWindowManager;
    private static Looper looper;

    public static void main(String... args) {

        Log.d(TAG, "run");
        System.out.println("run");

////        try {
////            Looper.prepare();
////            looper = Looper.myLooper();
////            AsyncServer server = new AsyncServer();
////            AsyncHttpServer httpServer = new AsyncHttpServer() {
////
////                protected boolean onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
////                    Log.i(TAG, request.getHeaders().toString());
////                    return super.onRequest(request, response);
////                }
////            };
////            Class cls = Class.forName("android.os.ServiceManager");
////            Method getServiceMethod = cls.getDeclaredMethod("getService", new Class[]{String.class});
////            IWindowManager wm = IWindowManager.Stub.asInterface((IBinder) getServiceMethod.invoke(null, new Object[]{"window"}));
////            httpServer.get("/screenshot.jpg", new AnonymousClass5(wm));
////            Log.i(TAG, "Server starting");
////            if (httpServer.listen(server, 53516) == null /*|| rawSocket == null */) {
////                System.out.println("No server socket?");
////                Log.e(TAG, "No server socket?");
////                throw new AssertionError("No server socket?");
////            }
////            System.out.println("Started");
////            Log.i(TAG, "Waiting for exit");
////            Looper.loop();
////            Log.i(TAG, "Looper done");
////            server.stop();
////            Log.i(TAG, "Done!");
////            System.exit(0);
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        } catch (NoSuchMethodException e) {
////            e.printStackTrace();
////        } catch (IllegalAccessException e) {
////            e.printStackTrace();
////        } catch (InvocationTargetException e) {
////            e.printStackTrace();
////        }
////
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
//            server.listen(asyncServer, 53516);
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
