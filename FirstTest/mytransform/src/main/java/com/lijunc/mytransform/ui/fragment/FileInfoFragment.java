package com.lijunc.mytransform.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lijunc.mytransform.AppContext;
import com.lijunc.mytransform.R;
import com.lijunc.mytransform.entity.FileInfo;
import com.lijunc.mytransform.ui.ChooseFileActivity;
import com.lijunc.mytransform.ui.adapter.FileInfoAdapter;
import com.lijunc.mytransform.utils.FileUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lijunc on 2017/9/6.
 */

public class FileInfoFragment extends Fragment {

    private List<FileInfo> mSFileInfoList;
    @Bind(R.id.gv)
    GridView gv;
    private FileInfoAdapter mAdapter;


    public static FileInfoFragment newInstance(){
        FileInfoFragment fragment = new FileInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_apk, container, false);
        ButterKnife.bind(this,inflate);
        init();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileInfo fileInfo = mSFileInfoList.get(position);
                if(AppContext.getAppContext().isExist(mSFileInfoList.get(position))){
                    AppContext.getAppContext().delFileInfo(fileInfo);
                }else{
                    AppContext.getAppContext().addFileInfo(fileInfo);
                }
                ChooseFileActivity activity=(ChooseFileActivity)getActivity();
                activity.setFileNum();
                mAdapter.notifyDataSetChanged();
            }
        });
        return inflate;
    }


    private void init() {
        new GetFileInfoListTask().executeOnExecutor(AppContext.MAIN_EXECUTOR);
    }

    private class GetFileInfoListTask extends AsyncTask<String,Integer,List<FileInfo>>{


        @Override
        protected List<FileInfo> doInBackground(String... params) {

            mSFileInfoList = FileUtils.getSpecificTypeFile(getActivity(), new String[]{FileInfo.EXTEND_JPG, FileInfo.EXTEND_JPEG});
            return mSFileInfoList;
        }

        @Override
        protected void onPostExecute(List<FileInfo> infos) {
            super.onPostExecute(infos);
            if(infos!=null&&infos.size()>0){
                mAdapter = new FileInfoAdapter(getActivity(), infos);
                gv.setAdapter(mAdapter);
            }
        }
    }
}
