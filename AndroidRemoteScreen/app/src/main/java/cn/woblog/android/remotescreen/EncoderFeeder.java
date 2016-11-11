package cn.woblog.android.remotescreen;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.view.IWindowManager;

/**
 * Created by renpingqing on 16/11/11.
 */

public class EncoderFeeder {


    public static Bitmap screenshot(IWindowManager wm) throws Exception {
        String surfaceClassName;
        Point size = SurfaceControlVirtualDisplayFactory.getCurrentDisplaySize(false);
        if (Build.VERSION.SDK_INT <= 17) {
            surfaceClassName = "android.view.Surface";
        } else {
            surfaceClassName = "android.view.SurfaceControl";
        }
        Bitmap b = (Bitmap) Class.forName(surfaceClassName).getDeclaredMethod("screenshot", new Class[]{Integer.TYPE, Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(size.x), Integer.valueOf(size.y)});
//        int rotation = wm.getRotation();
//        if (rotation == 0) {
//            return b;
//        }
//        Matrix m = new Matrix();
//        if (rotation == 1) {
//            m.postRotate(-90.0f);
//        } else if (rotation == 2) {
//            m.postRotate(-180.0f);
//        } else if (rotation == 3) {
//            m.postRotate(-270.0f);
//        }
//        return Bitmap.createBitmap(b, 0, 0, size.x, size.y, m, false);
        return b;
    }
}
