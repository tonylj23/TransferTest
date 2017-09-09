package com.lijunc.mytransform.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.lijunc.mytransform.AppContext;
import com.lijunc.mytransform.BaseActivity;
import com.lijunc.mytransform.R;
import com.lijunc.mytransform.entity.FileInfo;
import com.lijunc.mytransform.ui.fragment.FileInfoFragment;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lijunc on 2017/9/6.
 */

public class ChooseFileActivity extends BaseActivity {

    @Bind(R.id.choose_vp)
    ViewPager choose_vp;
    @Bind(R.id.file_next)
    Button file_next;
    @Bind(R.id.file_num)
    Button file_num;
    private FileInfoFragment mJpgFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_file);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_FILE);
            }
        }else{
            initData();
        }
        initData();
    }


    public void setFileNum(){
        Map<String, FileInfo> fileInfoMap = AppContext.getAppContext().getSenderFileInfoMap();
        if(fileInfoMap!=null&& fileInfoMap.size()>0){
            file_num.setText("已选（"+fileInfoMap.size()+"）");
        }else{
            file_num.setText("已选（0）");
        }
    }

    @OnClick({R.id.file_num,R.id.file_next})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.file_next:
                startActivity(new Intent(this,ChooseReceiverActivity.class));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_WRITE_FILE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //初始化
                initData();
            } else {
                // Permission Denied
                finish();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initData() {
        mJpgFragment = FileInfoFragment.newInstance();
        choose_vp.setAdapter(new ResPagerAdapter(getSupportFragmentManager()));
    }

    class ResPagerAdapter extends FragmentPagerAdapter{
        String[] sTitleArray;

        public ResPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public ResPagerAdapter(FragmentManager fm,String[] sTitleArray){
            this(fm);
            this.sTitleArray=sTitleArray;
        }

        @Override
        public Fragment getItem(int position) {
            return mJpgFragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "图片";
        }
    }
}
