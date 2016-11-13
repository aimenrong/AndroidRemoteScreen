package cn.woblog.java.remotescreen.util;

import java.io.EOFException;

/**
 * Created by renpingqing on 11/13/16.
 */

public class ByteUtil {
    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];

        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }

    public static int byte2int(byte[] res) {
//// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
//
//        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
//                | ((res[2] << 24) >>> 8) | (res[3] << 24);
//        return targets;

        int ch1 = res[0];
        int ch2 = res[1];
        int ch3 = res[2];
        int ch4 = res[3];
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }
}
