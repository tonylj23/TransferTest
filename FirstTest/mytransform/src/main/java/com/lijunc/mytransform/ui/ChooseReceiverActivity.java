package com.lijunc.mytransform.ui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lijunc.mytransform.BaseActivity;
import com.lijunc.mytransform.R;
import com.lijunc.mytransform.ui.adapter.WifiScanResultAdapter;
import com.lijunc.mytransform.utils.ListUtils;
import com.lijunc.mytransform.utils.WifiMgr;

import java.util.List;
import java.util.logging.LoggingMXBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lijunc on 2017/9/8.
 */

public class ChooseReceiverActivity extends BaseActivity {



    @Bind(R.id.choose_receiver_rv)
    RecyclerView choose_receiver_rv;
    private Object mWifiSacnResult;
    private List<ScanResult> mScanResultList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_receiver_layout);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        //查看WiFi状态，没打开就打开
        if(!WifiMgr.getInstance(getContext()).isWifiEnable()){
            WifiMgr.getInstance(getContext()).openWifi();
        }

        //6.0以上 wifi扫描要开启权限
        if(Build.VERSION.SDK_INT>=23){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission_group.LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE
                }, REQUEST_CODE_OPEN_GPS);
            }
        }else{
            upadateUI();
        }
        upadateUI();
    }

    private void upadateUI() {
        getWifiScanResult();
    }

    private void initData() {

    }

    /**
     * 获取WiFi扫描列表
     */
    public void getWifiScanResult() {
        WifiMgr.getInstance(getContext()).startScan();
        mScanResultList = WifiMgr.getInstance(getContext()).getScanResultList();
//        mScanResultList= ListUtils.filterWithNoPassword(mScanResultList);
        if(mScanResultList!=null){
            WifiScanResultAdapter adapter = new WifiScanResultAdapter(this, mScanResultList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            choose_receiver_rv.setAdapter(adapter);
            choose_receiver_rv.setLayoutManager(linearLayoutManager);
            adapter.setOnItemClickListener(new WifiScanResultAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Toast.makeText(ChooseReceiverActivity.this, mScanResultList.get(position).SSID.toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
