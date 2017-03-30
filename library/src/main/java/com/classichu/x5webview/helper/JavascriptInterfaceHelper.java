package com.classichu.x5webview.helper;

import android.content.Context;
import android.util.Log;

import static com.tencent.smtt.sdk.TbsReaderView.TAG;

/**
 * Created by louisgeek on 2016/11/14.
 */

public class JavascriptInterfaceHelper {

    private Context mContext;

    public JavascriptInterfaceHelper(Context context) {
        this.mContext = context;
    }

    /**
     * 必须添加 @android.webkit.JavascriptInterface注解
     *
     * @param imgUrl
     */
    @android.webkit.JavascriptInterface
    public void jsWillCallJavaFun_openImageShow(String imgUrl) {
        Log.d(TAG, "jsWillCallJavaFun_openImageShow: " + imgUrl);
    }
}
