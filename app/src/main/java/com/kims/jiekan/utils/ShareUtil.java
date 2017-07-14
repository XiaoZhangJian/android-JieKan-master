package com.kims.jiekan.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by zhangjian on 2017/5/9.
 */

public class ShareUtil  implements Serializable{

    private static ShareUtil mInstance;
    public static ShareUtil getInstance(){
        if (mInstance == null){
            synchronized (ShareUtil.class){
                if (mInstance == null){
                    mInstance = new ShareUtil();
                }
            }
        }
        return mInstance;
    }


    public void shareText(Activity mAct, String params){
        Intent sharedIntent = new Intent();
        //设置动作为Intent.ACTION_SEND
        sharedIntent.setAction(Intent.ACTION_SEND);
        //设置为文本类型
        sharedIntent.setType("text/*");
        sharedIntent.putExtra(Intent.EXTRA_TEXT, params); //设置要分享的内容
        mAct.startActivity(Intent.createChooser(sharedIntent, "分享到："));
    }

    public void shareImg(Activity mAct, Uri uri){
        Intent sharedIntent = new Intent();
        //设置动作为Intent.ACTION_SEND
        sharedIntent.setAction(Intent.ACTION_SEND);
        //设置为文本类型
        sharedIntent.setType("image/*");
        sharedIntent.putExtra(Intent.EXTRA_TEXT, uri); //设置要分享的内容
        mAct.startActivity(Intent.createChooser(sharedIntent, "分享到："));
    }

}
