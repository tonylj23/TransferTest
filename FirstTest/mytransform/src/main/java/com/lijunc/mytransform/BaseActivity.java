package com.lijunc.mytransform;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lijunc.mytransform.utils.StatusBarUtils;

/**
 * Created by lijunc on 2017/9/6.
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * 写文件的请求码
     */
    public static final int  REQUEST_CODE_WRITE_FILE = 200;

    /**
     * 打开GPS的请求码
     */
    public static final int  REQUEST_CODE_OPEN_GPS = 205;

    Context mContext;
    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext=this;
        StatusBarUtils.setStatusBarAndBUttomBarTranslucent(this);
        super.onCreate(savedInstanceState);
    }

    /**
     * 获取上下文
     * @return
     */
    public Context getContext(){
        return mContext;
    }


    protected void showProgressDialog(){
        if(mProgressDialog==null){
            mProgressDialog=new ProgressDialog(mContext);
        }

        mProgressDialog.setMessage("正在加载中。。。");
        mProgressDialog.show();
    }

    protected void hideProgressDialog(){
        if(mProgressDialog!=null&&mProgressDialog.isShowing()){
            mProgressDialog.hide();
            mProgressDialog=null;
        }
    }

}
