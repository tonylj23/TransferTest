package com.lijunc.mytransform.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by lijunc on 2017/9/6.
 */

public class StatusBarUtils {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarAndBUttomBarTranslucent(Activity activity){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window window = activity.getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            );
        }
    }
}
