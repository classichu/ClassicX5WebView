package com.classichu.x5webview.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.classichu.x5webview.ClassicX5WebViewActivity;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by louisgeek on 2017/3/30.
 */

public class ClassicX5WebViewHelper {
    private static final String TAG = "ClassicX5WebViewHelper";

    public static void initFirstOnApp(Context context) {
        /**
         *App 首次就可以加载 x5 内核
         App 在启动后（例如在 Application 的 onCreate 中）立刻调用 QbSdk 的预加载接口 initX5Environment ，可参考接入示例，
         第一个参数传入 context，第二个参数传入 callback，不需要 callback 的可以传入 null，initX5Environment
         内部会创建一个线程向后台查询当前可用内核版本号，这个函数内是异步执行所以不会阻塞 App 主线程，这个函数
         内是轻量级执行所以对 App 启动性能没有影响，当 App 后续创建 webview 时就可以首次加载 x5 内核了
         */
        QbSdk.initX5Environment(context, new QbSdk.PreInitCallback()

        {
            @Override
            public void onCoreInitFinished() {
                Log.d(TAG, "QbSdk onCoreInitFinished: ");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.d(TAG, "QbSdk onViewInitFinished: " + b);
            }
        });
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {

                Log.d(TAG, "QbSdk onDownloadFinish: ");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d(TAG, "QbSdk onInstallFinish: " + i);
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d(TAG, "QbSdk onDownloadProgress: " + i);
            }
        });
    }

    public static void setDataAndToImageShow(Context context,String url
                                             ) {
        setDataAndToImageShow(context,url,true);
    }
    public static void setDataAndToImageShow(Context context,String url,
                                             boolean isTitleCenter) {
        Intent intent=new Intent(context,ClassicX5WebViewActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("url", url);
        bundle.putBoolean("isTitleCenter",isTitleCenter);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
