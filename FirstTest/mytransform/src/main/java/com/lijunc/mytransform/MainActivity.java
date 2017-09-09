package com.lijunc.mytransform;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lijunc.mytransform.ui.ChooseFileActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Bind(R.id.send_btn)
    Button send_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_WRITE_FILE);
//            }
//        }

    }



    @OnClick({R.id.send_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn:
                Intent intent = new Intent(this, ChooseFileActivity.class);
                intent.putExtra(Constant.KEY_WEB_TRANSFER_FLAG, false);
                startActivity(intent);
                break;
        }
    }
}
