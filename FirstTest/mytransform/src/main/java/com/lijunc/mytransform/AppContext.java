package com.lijunc.mytransform;

import android.app.Application;

import com.lijunc.mytransform.entity.FileInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by lijunc on 2017/9/6.
 */

public class AppContext extends Application {


    //普通线程
    public static Executor MAIN_EXECUTOR = Executors.newFixedThreadPool(5);


    //发送线程
    public static Executor FILE_SENDER_EXECUTOR=Executors.newSingleThreadExecutor();

    static AppContext mAppContext;

    Map<String,FileInfo> mSenderFileInfoMap=new HashMap<String,FileInfo>();

    Map<String,FileInfo> mReceiverFileInfoMap=new HashMap<String,FileInfo>();

    @Override
    public void onCreate() {
        super.onCreate();
        this.mAppContext=this;
    }

    public static AppContext getAppContext(){
        return mAppContext;
    }

    //发送方
    /**
     * 向map中添加FileInfo
     */
    public void addFileInfo(FileInfo fileInfo){
        if(!mSenderFileInfoMap.containsKey(fileInfo.getFilePath())){
            mSenderFileInfoMap.put(fileInfo.getFilePath(),fileInfo);
        }
    }

    /**
     * 更新FileInfo
     */
    public void updateFileInfo(FileInfo fileInfo){
        mSenderFileInfoMap.put(fileInfo.getFilePath(),fileInfo);
    }
    /**
     * 删除map中FileInfo
     */
    public void delFileInfo(FileInfo fileInfo){
        if(mSenderFileInfoMap.containsKey(fileInfo.getFilePath())){
            mSenderFileInfoMap.remove(fileInfo.getFilePath());
        }
    }
    /**
     * 查找指定FileInfo是否存在
     */
    public boolean isExist(FileInfo fileInfo){
        if(fileInfo==null)
            return false;
        return mSenderFileInfoMap.containsKey(fileInfo.getFilePath());
    }

    /**
     * 判断发送集合中是否有数据
     */
    public boolean isFileInfoMapExist(){
        if(mSenderFileInfoMap==null||mSenderFileInfoMap.size()<0){
            return false;
        }
        return true;
    }

    /**
     * 获取sendermap
     */
    public Map<String,FileInfo> getSenderFileInfoMap(){
        return mSenderFileInfoMap;
    }

    /**
     * 获取即将发送的所有文件的长度
     */
    public long getAllSendFileSize(){
        long total=0;
        for(FileInfo fileInfo:mSenderFileInfoMap.values()){
            if(fileInfo!=null){
                total+=fileInfo.getSize();
            }
        }
        return total;
    }


    //接收方
    /**
     * 添加一个FileInfo
     * @param fileInfo
     */
    public void addReceiverFileInfo(FileInfo fileInfo){
        if(!mReceiverFileInfoMap.containsKey(fileInfo.getFilePath())){
            mReceiverFileInfoMap.put(fileInfo.getFilePath(), fileInfo);
        }
    }

    /**
     * 更新FileInfo
     * @param fileInfo
     */
    public void updateReceiverFileInfo(FileInfo fileInfo){
        mReceiverFileInfoMap.put(fileInfo.getFilePath(), fileInfo);
    }
    /**
     * 获取全局变量中的FileInfoMap
     * @return
     */
    public Map<String, FileInfo> getReceiverFileInfoMap(){
        return mReceiverFileInfoMap;
    }


    /**
     * 获取即将接收文件列表的总长度
     * @return
     */
    public long getAllReceiverFileInfoSize(){
        long total = 0;
        for(FileInfo fileInfo : mReceiverFileInfoMap.values()){
            if(fileInfo != null){
                total = total + fileInfo.getSize();
            }
        }
        return total;
    }

}
