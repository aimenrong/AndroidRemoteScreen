package cn.woblog.java.remotescreen.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpingqing on 11/13/16.
 */
public class CommandUtil {

    public static void runCommand(String c) {
        try {
            System.out.println(c);
            Process process = Runtime.getRuntime().exec(c);

            List<String> processList = new ArrayList<String>();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
//
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
