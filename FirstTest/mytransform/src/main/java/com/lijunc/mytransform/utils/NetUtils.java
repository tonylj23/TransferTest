package com.lijunc.mytransform.utils;

import java.io.IOException;

/**
 * Created by lijunc on 2017/9/9.
 */

public class NetUtils {

    //查看要连接的ip是否能够Ping通
    public static boolean pingIPAddress(String ipAddress){
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/ping -c 1 -w 100 " + ipAddress);
            int status=process.waitFor();
            if(status==0){
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
