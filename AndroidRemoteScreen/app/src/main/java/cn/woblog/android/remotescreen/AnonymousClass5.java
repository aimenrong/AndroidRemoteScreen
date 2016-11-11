package cn.woblog.android.remotescreen;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.IWindowManager;

import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import java.io.ByteArrayOutputStream;

/**
 * Created by renpingqing on 16/11/11.
 */


class AnonymousClass5 implements HttpServerRequestCallback {
    final /* synthetic */ IWindowManager val$wm;

    AnonymousClass5(IWindowManager iWindowManager) {
        this.val$wm = iWindowManager;
    }

    public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        if (true) {
            Log.i(Main.TAG, "screenshot authentication success");
            try {
                Bitmap bitmap = EncoderFeeder.screenshot(this.val$wm);
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bout);
                bout.flush();
                response.send("image/jpeg", bout.toByteArray());
                return;
            } catch (Exception e) {
                response.code(500);
                response.send(e.toString());
                return;
            }
        }
        Log.i(Main.TAG, "screenshot authentication failed");
        response.code(401);
        response.send("Not Authorized.");
    }
}