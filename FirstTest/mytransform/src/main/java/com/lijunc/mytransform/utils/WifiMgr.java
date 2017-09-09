package com.lijunc.mytransform.utils;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by lijunc on 2017/9/8.
 */

public class WifiMgr {
    /**
     * 创建WifiConfiguration的类型
     */
    public static final int WIFICIPHER_NOPASS = 1;
    public static final int WIFICIPHER_WEP = 2;
    public static final int WIFICIPHER_WPA = 3;

    private Context mContext;
    private final WifiManager mWifiManager;

    private static WifiMgr wifiMgr;
    private List<ScanResult> mScanResultList;
    private List<WifiConfiguration> mWifiConfigurations;


    public WifiMgr(Context context) {
        mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public List<ScanResult> getScanResultList() {
        return mScanResultList;
    }

    public static WifiMgr getInstance(Context context){
        if(wifiMgr==null){
            synchronized (WifiMgr.class){
                if(wifiMgr==null){
                    wifiMgr = new WifiMgr(context);
                }
            }
        }
        return wifiMgr;
    }

    public void openWifi(){
        if(!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(true);
        }
    }


    /**
     * 判断WiFi是否开启
     */
    public boolean isWifiEnable(){
        return mWifiManager==null?false:mWifiManager.isWifiEnabled();
    }

    /**
     * 扫描wifi
     */
    public void startScan(){
        mWifiManager.startScan();
        mScanResultList = mWifiManager.getScanResults();
        mWifiConfigurations = mWifiManager.getConfiguredNetworks();
    }


    //添加到指定的wifi中
    public boolean addNetwork(WifiConfiguration wf){

        //断开现有连接
        disconnectCurrentNetwork();

        //连接到新连接
        int i = mWifiManager.addNetwork(wf);
        boolean b = mWifiManager.enableNetwork(i, true);
        return b;
    }

    private boolean disconnectCurrentNetwork() {
        if(mWifiManager!=null&&mWifiManager.isWifiEnabled()){
            int netId = mWifiManager.getConnectionInfo().getNetworkId();
            mWifiManager.disableNetwork(netId);
            return mWifiManager.disconnect();
        }
        return false;
    }

    //创建wifiConfiguration
    public static WifiConfiguration createWificfg(String ssid,String password,int type){
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.allowedAuthAlgorithms.clear();
        configuration.allowedGroupCiphers.clear();
        configuration.allowedKeyManagement.clear();
        configuration.allowedPairwiseCiphers.clear();
        configuration.allowedProtocols.clear();


        configuration.SSID="\""+ssid+"\"";

        if(type==WIFICIPHER_NOPASS){
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }else if(type==WIFICIPHER_WEP){
            configuration.hiddenSSID=true;
            configuration.wepKeys[0]="\""+password+"\"";
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            configuration.wepTxKeyIndex = 0;
        }else if(type==WIFICIPHER_WPA){
            configuration.preSharedKey = "\""+password+"\"";
            configuration.hiddenSSID = true;
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            configuration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            configuration.status = WifiConfiguration.Status.ENABLED;
        }
        return configuration;
    }

    //获取需要连接的wifi的ip
    public String getIPAddressFromHotspot(){
        //android 中WiFi AP的默认IP为"192.168.43.1"
        String ipAddress="192.168.43.1";
        DhcpInfo info = mWifiManager.getDhcpInfo();
        int address = info.gateway;
        //将网关转化成IP地址
        ipAddress=((address & 0xFF)
                + "." + ((address >> 8) & 0xFF)
                + "." + ((address >> 16) & 0xFF)
                + "." + ((address >> 24) & 0xFF));
        return ipAddress;

    }

    /**
     * 关闭wifi
     */
    public void disableWifi(){
        if(mWifiManager!=null){
            mWifiManager.setWifiEnabled(false);
        }
    }

}
