package com.example.wudongchuan.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * 自定义的Android代码和JavaScript代码之间的桥梁类
 */
public class WebInterfaceHelper {
    private Context mContext;

    /**
     * 设置 context 用
     */
    public WebInterfaceHelper(Context c) {
        mContext = c;
    }

    /**
     * js调打电话功能
     */
    // 如果target 大于等于API 17，则需要加上如下注解
    @JavascriptInterface
    public void callPhone(final String str) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                //用intent启动拨打电话
                String tellString = str;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tellString));
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * js调打添加车辆功能
     */
    @JavascriptInterface
    public void addVehicles(final String url) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                //添加车辆
//                mContext.startActivity(new Intent(mContext, AddCarActivity.class));
                Toast.makeText(mContext, "addVehicles",Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * js调打提示功能
     */
    @JavascriptInterface
    public void toolTip(final String message) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                //提示
                Toast.makeText(mContext, message,Toast.LENGTH_LONG).show();
            }
        });
    }

}