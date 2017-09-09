package com.lijunc.mytransform.utils;

import android.net.wifi.ScanResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunc on 2017/9/8.
 */

public class ListUtils {
    public static final String NO_PASSWORD = "[ESS]";
    public static final String NO_PASSWORD_WPS = "[WPS][ESS]";
    public static List<ScanResult> filterWithNoPassword(List<ScanResult> results){
        if(results==null||results.size()==0){
            return results;
        }

        List<ScanResult> resultList = new ArrayList<>();
        for(ScanResult result:results){
            if(result.capabilities!=null&&result.capabilities.equals(NO_PASSWORD)
                    || result.capabilities != null && result.capabilities.equals(NO_PASSWORD_WPS)){
                resultList.add(result);
            }
        }
        return resultList;
    }
}
