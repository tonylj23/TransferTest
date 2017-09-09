package com.lijunc.mytransform.entity;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lijunc on 2017/9/6.
 */

public class FileInfo implements Serializable{
    /**
     * 常见文件拓展名
     */
    public static final String EXTEND_APK = ".apk";
    public static final String EXTEND_JPEG = ".jpeg";
    public static final String EXTEND_JPG = ".jpg";
    public static final String EXTEND_PNG = ".png";
    public static final String EXTEND_MP3 = ".mp3";
    public static final String EXTEND_MP4 = ".mp4";

    //必要属性
    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件类型
     */
    private int fileType;

    //非必要属性
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件大小描述
     */
    private String sizeDesc;
    /**
     * 文件缩略图
     */
    private Bitmap mBitmap;
    /**
     * 文件额外信息
     */
    private String extra;
    /**
     * 已经处理的大小（读或写）
     */
    private long procceed;
    /**
     *传送结果
     */
    private int result;

    public FileInfo() {
    }

    public FileInfo(String filePath, long size) {
        this.filePath = filePath;
        this.size = size;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSizeDesc() {
        return sizeDesc;
    }

    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public long getProcceed() {
        return procceed;
    }

    public void setProcceed(long procceed) {
        this.procceed = procceed;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static String toJsonStr(FileInfo fileInfo){
        String jsonStr="";
        JSONObject object = new JSONObject();
        try {
            object.put("filePath",fileInfo.getFilePath());
            object.put("fileType",fileInfo.getFileType());
            object.put("size",fileInfo.getSize());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public static FileInfo toObject(String jsonStr){
        FileInfo info = new FileInfo();
        try {
            JSONObject object = new JSONObject(jsonStr);
            String filePath = (String) object.get("filePath");
            long size =  object.getLong("size");
            int fileType =  object.getInt("fileType");
            info.setFilePath(filePath);
            info.setFileType(fileType);
            info.setSize(size);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static String toJsonArrayStr(List<FileInfo> fileInfoList){
        JSONArray array = new JSONArray();
        if(fileInfoList!=null){
            for(FileInfo fileInfo:fileInfoList){
                if(fileInfo!=null){
                    try {
                        array.put(new JSONObject(toJsonStr(fileInfo)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return array.toString();
    }
}
