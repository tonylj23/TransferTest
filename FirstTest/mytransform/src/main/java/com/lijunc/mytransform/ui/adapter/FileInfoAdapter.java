package com.lijunc.mytransform.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lijunc.mytransform.AppContext;
import com.lijunc.mytransform.R;
import com.lijunc.mytransform.entity.FileInfo;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by lijunc on 2017/9/7.
 */

public class FileInfoAdapter extends BaseAdapter {


    private Context mContext;
    private List<FileInfo> mFileInfoList;


    public FileInfoAdapter(Context context, List<FileInfo> fileInfoList) {
        mContext = context;
        mFileInfoList = fileInfoList;
    }

    @Override
    public int getCount() {
        return mFileInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFileInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImgHolder imgHolder;
        if(convertView==null){
            convertView = View.inflate(mContext, R.layout.file_adapter_item, null);
            imgHolder = new ImgHolder();
            imgHolder.iv_shortcut= (ImageView) convertView.findViewById(R.id.iv_shortcut);
            imgHolder.icon_ok_tick= (ImageView) convertView.findViewById(R.id.icon_ok_tick);
            convertView.setTag(imgHolder);
        }else{
            imgHolder = (ImgHolder) convertView.getTag();
        }
        if(mFileInfoList!=null&&mFileInfoList.get(position)!=null){
            Glide.with(mContext)
                    .load(mFileInfoList.get(position).getFilePath())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .into(imgHolder.iv_shortcut);

            if (AppContext.getAppContext().isExist(mFileInfoList.get(position))) {
                imgHolder.icon_ok_tick.setVisibility(View.VISIBLE);
            }else{
                imgHolder.icon_ok_tick.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    static class ImgHolder{
        ImageView iv_shortcut;
        ImageView icon_ok_tick;
    }
}
