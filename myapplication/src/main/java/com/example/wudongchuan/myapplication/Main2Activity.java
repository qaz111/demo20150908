package com.example.wudongchuan.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends Activity {
    private WebView mWebView;
    private Button btnShowInfo;
    private WebInterfaceHelper js;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //初始化控件
        mWebView = (WebView) findViewById(R.id.wv_test);
        btnShowInfo = (Button) findViewById(R.id.btn_showmsg);
        btnShowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int strings=SecondLib.sumMethod(3,4);
                Toast.makeText(Main2Activity.this,strings+"",Toast.LENGTH_LONG).show();
            }
        });
        //实例化js对象
        js = new WebInterfaceHelper(this);

        initData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initData() {
        //设置WebView属性，能够执行Javascript脚本
        mWebView.getSettings().setJavaScriptEnabled(true);

        WebSettings mWebSettings = mWebView.getSettings();
        //启用数据库
        mWebSettings.setDatabaseEnabled(true);
        //设置定位的数据库路径
        mWebSettings.setGeolocationDatabasePath(getApplicationContext().getDir("database", MODE_PRIVATE).getPath());
        //启用地理定位
        mWebSettings.setGeolocationEnabled(true);
        //开启DomStorage缓存
        mWebSettings.setDomStorageEnabled(true);

        mWebSettings.setLightTouchEnabled(true);

        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setAllowFileAccess(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                // TODO Auto-generated method stub
                callback.invoke(origin, true, true);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                // TODO Auto-generated method stub
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }
        });

        //提高渲染的优先级
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

//		if (isNetworkAvailableUtils.isNetworkAvailable(this)) {
//			mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//		} else {
//			//只要本地有缓存，都使用缓存。本地没有缓存时才从网络上获取。
//			mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//		}
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
//        mWebView.loadUrl("file:///android_asset/jstest.html");
//        mWebView.loadUrl("http://192.168.0.35/jstest.html");
        mWebView.loadUrl("http://192.168.0.35");
//        mWebView.loadUrl("http://119.145.230.124:8060/AppointmentOnsiteMaintenance/Index");
        mWebView.addJavascriptInterface(new WebInterfaceHelper(Main2Activity.this), "wst");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url); // 在当前的webview中跳转到新的url

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
//@SuppressLint("SetJavaScriptEnabled")
// @JavascriptInterface