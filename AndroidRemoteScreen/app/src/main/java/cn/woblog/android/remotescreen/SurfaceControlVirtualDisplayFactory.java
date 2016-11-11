package cn.woblog.android.remotescreen;

import android.graphics.Point;
import android.os.IBinder;
import android.view.IWindowManager;

import java.lang.reflect.Method;

/**
 * Created by renpingqing on 16/11/11.
 */

public class SurfaceControlVirtualDisplayFactory {
    public static Point getCurrentDisplaySize(boolean rotate) {
        int v5;
        IWindowManager v8;
        try {
            Method v4 = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String
                    .class);
            Point v1 = new Point();
//            if(Build.VERSION.SDK_INT >= 18) {
            v8 = IWindowManager.Stub.asInterface((IBinder) v4.invoke(null, "window"));
            v8.getBaseDisplaySize(0, v1);
//                v5 = v8.getRotation();
//            }
//            else if(Build.VERSION.SDK_INT == 17) {
//                DisplayInfo v0 = android.hardware.display.IDisplayManager.Stub.asInterface(v4.invoke(
//                        null, "display")).getDisplayInfo(0);
//                v1.x = DisplayInfo.class.getDeclaredField("logicalWidth").get(v0).intValue();
//                v1.y = DisplayInfo.class.getDeclaredField("logicalHeight").get(v0).intValue();
//                v5 = DisplayInfo.class.getDeclaredField("rotation").get(v0).intValue();
//            }
//            else {
//                v8 = Stub.asInterface(v4.invoke(null, "window"));
//                v8.getRealDisplaySize(v1);
//                v5 = v8.getRotation();
//            }
//
//            if((rotate) && (v5 == 1 || v5 == 3)) {
//                int v7 = v1.x;
//                v1.x = v1.y;
//                v1.y = v7;
//            }

            return v1;
        } catch (Exception v3) {
            throw new AssertionError(v3);
        }
    }
}
