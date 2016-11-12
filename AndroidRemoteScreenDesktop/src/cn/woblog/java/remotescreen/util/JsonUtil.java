package cn.woblog.java.remotescreen.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


/**
 * Created by ren on 2015/8/28 0028.
 */
public class JsonUtil {
    public static <T> T toObject(JsonObject element, Class<T> clazz) {
        if (element == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(element.toString(), clazz);
    }

    public static <T> T toObject(String element, Class<T> clazz) {
        if (element == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(element, clazz);
    }

    public static String toJson(Object element) {
        if (element == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(element);
    }

}
