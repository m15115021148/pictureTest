package com.geek.imagedemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MainActivity mContext;
    private ImageView iv;
    private Button bt;
    private PopupWindow popDialog;//dialog
    private SystemFunUtil imgUtil;// 拍照


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        iv = (ImageView) findViewById(R.id.img);
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(this);

        imgUtil = new SystemFunUtil(mContext);
    }

    @Override
    public void onClick(View v) {
        if (v == bt){
            final String[] values = {"拍照","本地相册"};
            popDialog = DialogUtil.customPopShowWayDialog(mContext, DialogUtil.DialogShowWay.FROM_DOWN_TO_UP, values,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView tv = (TextView) v;
                            String name = tv.getText().toString().trim();
                            if (name.equals(values[0])){
                                imgUtil.openCamera(SystemFunUtil.SYSTEM_IMAGE, 100);
                            }
                            if (name.equals(values[1])){
                                imgUtil.openCamera(SystemFunUtil.SYSTEM_IMAGE_PHONE, 101);
                            }
                            popDialog.dismiss();
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case 100:// 拍照
                    File imgFile = imgUtil.getImgFile();//保存图片的文件
                    x.image().bind(iv,imgFile.getPath());
                    break;
                case 101:// 拍照
                    Uri uri = data.getData();
                    Log.e("uri", uri.toString());
                    ContentResolver cr = this.getContentResolver();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        iv.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.e("Exception", e.getMessage(),e);
                    }
                    break;
            }
        }

    }
}
