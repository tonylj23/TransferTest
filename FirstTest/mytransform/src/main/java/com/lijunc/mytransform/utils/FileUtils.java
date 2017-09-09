package com.lijunc.mytransform.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.lijunc.mytransform.entity.FileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunc on 2017/9/6.
 */

public class FileUtils {


    public static List<FileInfo> getSpecificTypeFile(Context context,String[] extensions){
        List<FileInfo> fileInfoList=new ArrayList<>();
        //存储卡的uri
        Uri uri = MediaStore.Files.getContentUri("external");
        //找出文件路径和含有后缀名的文件名
        String[] projection = {MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.TITLE};
        String selection="";
        for(int i=0;i<extensions.length;i++){
            if(i!=0){
                selection+=" OR ";
            }
            selection=selection+MediaStore.Files.FileColumns.DATA
                    +" LIKE '%"+extensions[i]+"'";

            //排列条件，按时间降序
            String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;
            Cursor cursor = context.getContentResolver().query(uri, projection, selection, null, sortOrder);
            if(cursor!=null){
                while (cursor.moveToNext()){
                    String data = cursor.getString(0);
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFilePath(data);
                    long size=0;
                    File file = new File(data);
                    size= file.length();
                    fileInfo.setSize(size);
                    fileInfoList.add(fileInfo);
                }
            }

        }
        return fileInfoList;

    }

}
