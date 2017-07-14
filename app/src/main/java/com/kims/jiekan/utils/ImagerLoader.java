package com.kims.jiekan.utils;

import android.app.Activity;
import android.os.Environment;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by zhangjian on 2017/7/12.
 */

public class ImagerLoader {

    private static ImagerLoader instance;
    private Activity context;

    public static ImagerLoader getDefault() {
        if (instance == null) {
            synchronized (ImagerLoader.class) {
                if (instance == null) {
                    instance = new ImagerLoader();
                }
            }
        }
        return instance;
    }

    public ImagerLoader init(Activity activity) {
        this.context = activity;
        return this;
    }

    //Glide保存图片
    public ImagerLoader savePicture(String url) {
        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    savaFileToSD(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return this;
    }


    //往SD卡写入文件的方法
    public void savaFileToSD(byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/MeiZhi/Data";
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            String filename = filePath + "/" + System.currentTimeMillis() + ".jpg";
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流
            Toast.makeText(context, "图片已成功保存" + filename, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();

    }

}
