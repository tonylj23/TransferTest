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

import com.lijunc.mytransform.AppContext;
import com.lijunc.mytransform.BaseActivity;
import com.lijunc.mytransform.Constant;
import com.lijunc.mytransform.R;
import com.lijunc.mytransform.entity.FileInfo;
import com.lijunc.mytransform.ui.adapter.WifiScanResultAdapter;
import com.lijunc.mytransform.utils.ListUtils;
import com.lijunc.mytransform.utils.NetUtils;
import com.lijunc.mytransform.utils.WifiMgr;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
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
    private String mTargetIpAddress;
    private DatagramSocket mDatagramSocket;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_receiver_layout);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        //查看WiFi状态，没打开就打开
        if (!WifiMgr.getInstance(getContext()).isWifiEnable()) {
            WifiMgr.getInstance(getContext()).openWifi();
        }

        //6.0以上 wifi扫描要开启权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE
                }, REQUEST_CODE_OPEN_GPS);
            }
        } else {
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
        if (mScanResultList != null) {
            WifiScanResultAdapter adapter = new WifiScanResultAdapter(this, mScanResultList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            choose_receiver_rv.setAdapter(adapter);
            choose_receiver_rv.setLayoutManager(linearLayoutManager);
            adapter.setOnItemClickListener(new WifiScanResultAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    ScanResult scanResult = mScanResultList.get(position);
                    Toast.makeText(ChooseReceiverActivity.this, scanResult.SSID.toString(), Toast.LENGTH_LONG).show();

                    //连接到所点击的网络上
                    String ssid = scanResult.SSID;
                    WifiMgr.getInstance(getContext()).openWifi();
                    WifiMgr.getInstance(getContext()).addNetwork(WifiMgr.createWificfg(ssid, null, WifiMgr.WIFICIPHER_NOPASS));


                    //通过UDP告诉接收方开启接收文件的服务


                }
            });
        }
    }

    //开启发送方的服务线程
    private Runnable createSendServerRunnable(final String serverIP) {
        return new Runnable() {
            @Override
            public void run() {
                //wifi连接上后获取Ip
                int count = 0;
                int serverPort = Constant.DEFAULT_SERVER_COM_PORT;
                try {
                    while (serverIP.equals(Constant.DEFAULT_UNKOWN_IP)
                            && count < Constant.DEFAULT_TRY_TIME) {

                        Thread.sleep(1000);
                        mTargetIpAddress = WifiMgr.getInstance(getContext()).getIPAddressFromHotspot();
                        count++;
                    }

                    count=0;
                    while(!NetUtils.pingIPAddress(mTargetIpAddress)&&count<Constant.DEFAULT_TRY_TIME){
                        Thread.sleep(500);
                        count++;
                    }
                    mDatagramSocket = new DatagramSocket(serverPort);
                    byte[] bytes = new byte[1024];
                    byte[] sendData=null;
                    InetAddress inetAddress = InetAddress.getByName(mTargetIpAddress);

                    //将要发送的文件列表发送给接收方
                    sendFileInfosToReceiver(serverPort,mTargetIpAddress);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendFileInfosToReceiver(int port, String address) throws IOException{
        Map<String, FileInfo> senderFileInfoMap = AppContext.getAppContext().getSenderFileInfoMap();

    }

}
