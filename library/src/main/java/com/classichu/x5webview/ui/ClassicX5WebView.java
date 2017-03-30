package com.classichu.x5webview.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import static android.R.attr.defaultValue;
import static com.tencent.smtt.sdk.TbsReaderView.TAG;

/**
 * Created by louisgeek on 2017/3/30.
 */

public class ClassicX5WebView extends WebView {
    private Context mContext;

    public ClassicX5WebView(Context context) {
        this(context, null);
    }

    public ClassicX5WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClassicX5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        mContext = context;
        init();
    }

    private void init() {
        /**
         *WebSettings
         */
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        //webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(mContext.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(mContext.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(mContext.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);


        //
        this.setWebViewClient(new WebViewClient() {

            /**
             * 防止加载网页时调起系统浏览器
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (mX5WebViewDelegate != null) {
                    return mX5WebViewDelegate.shouldOverrideUrlLoading(webView, url);
                }
                return super.shouldOverrideUrlLoading(webView, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                return super.shouldInterceptRequest(webView, webResourceRequest);
            }

            @Override
            public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
                super.onPageStarted(webView, url, bitmap);
                if (mX5WebViewDelegate != null) {
                    mX5WebViewDelegate.onPageStarted(webView, url, bitmap);
                }

            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                if (mX5WebViewDelegate != null) {
                     mX5WebViewDelegate.onPageFinished(webView, url);
                }
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String s, String s1) {
                super.onReceivedHttpAuthRequest(webView, httpAuthHandler, s, s1);
                boolean flag = httpAuthHandler.useHttpAuthUsernamePassword();
                Log.i("xxx", "useHttpAuthUsernamePassword is" + flag);
            }

            @Override
            public void onDetectedBlankScreen(String s, int i) {
                super.onDetectedBlankScreen(s, i);
            }
        });


        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String title) {
                super.onReceivedTitle(webView, title);
                if (mX5WebViewDelegate != null) {
                    mX5WebViewDelegate.onReceivedTitle(webView, title);
                }
            }

            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);
                if (mX5WebViewDelegate != null) {
                     mX5WebViewDelegate.onProgressChanged(webView, newProgress);
                }
            }

            @Override
            public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult) {
                if (mX5WebViewDelegate != null) {
                    return mX5WebViewDelegate.onJsAlert(webView, url, message, defaultValue, jsResult);
                }
                return super.onJsAlert(webView, url, message, jsResult);

            }

            @Override
            public boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult jsPromptResult) {
                if (mX5WebViewDelegate != null) {
                    return mX5WebViewDelegate.onJsPrompt(webView, url, message, defaultValue, jsPromptResult);
                }
                return super.onJsPrompt(webView, url, message, defaultValue, jsPromptResult);
            }

            @Override
            public boolean onJsConfirm(WebView webView, String url, String message, JsResult jsResult) {
                if (mX5WebViewDelegate != null) {
                    return mX5WebViewDelegate.onJsConfirm(webView, url, message, jsResult);
                }
                return super.onJsConfirm(webView, url, message, jsResult);
            }

            @Override
            public boolean onJsTimeout() {
                if (mX5WebViewDelegate != null) {
                    return mX5WebViewDelegate.onJsTimeout();
                }
                return super.onJsTimeout();
            }
        });


        this.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType,
                                        long contentLength) {
                    if (mX5WebViewDelegate!=null){
                        mX5WebViewDelegate.onDownloadStart(mContext,url,userAgent,contentDisposition,mimeType,contentLength);
                    }
            }
        });


        /**
         *
         */
        final WebView.HitTestResult hitTestResult = this.getHitTestResult();
        this.getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final String imagePath = hitTestResult.getExtra();
                switch (hitTestResult.getType()) {
                    //获取点击的标签是否为图片
                    case WebView.HitTestResult.IMAGE_TYPE:
                        // Toast.makeText(getApplicationContext(), "IMAGE_TYPE"+ path, Toast.LENGTH_LONG).show();
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                        // Toast.makeText(getApplicationContext(), "SRC_IMAGE_ANCHOR_TYPE"+ path, Toast.LENGTH_LONG).show();
                        if (mX5WebViewDelegate != null) {
                            mX5WebViewDelegate.onLongClickView(v,imagePath);
                        }
                        break;
                }
                Log.d(TAG, "hitTestResult:" + hitTestResult.getType());
                Log.d(TAG, "getView onLongClick: ");
                // return false;
                return true;
            }
        });

    }

    private X5WebViewDelegate mX5WebViewDelegate;
    public void setX5WebViewDelegate(X5WebViewDelegate x5WebViewDelegate) {
        this.mX5WebViewDelegate = x5WebViewDelegate;
    }

    public abstract static class X5WebViewDelegate {
        public boolean onLongClickView(View view,String imagePath) {
            return false;
        }

        public boolean onJsTimeout() {
            return false;
        }

        public boolean onJsConfirm(WebView webView, String url, String message, JsResult jsResult) {
            return false;
        }

        public boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult jsPromptResult) {
            return false;
        }

        public boolean onJsAlert(WebView webView, String url, String message, int defaultValue, JsResult jsResult) {
            jsResult.confirm();
            return true;
        }

        public void onProgressChanged(WebView webView, int newProgress) {
        }

        public void onReceivedTitle(WebView webView, String title) {
        }

        public void onDownloadStart(Context context,String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

        public void onPageFinished(WebView webView, String url) {
            Log.d(TAG, "onPageFinished: " + url);

            /**
             * 有链接也会add
             */
            String javascriptInterfaceHelper_openImageShow_NeedAddJsStr =
                    "javascript:(function(){"
                            + "  var objs = document.getElementsByTagName(\"img\"); "
                            + "  for(var i=0;i<objs.length;i++){"
                            + "     objs[i].onclick=function(){"
                            + "          window.javascriptInterfaceHelper.jsWillCallJavaFun_openImageShow(this.src);"
                            + "     }"
                            + "  }"
                            + "})()";
            // KLog.d(javascriptInterfaceHelper_openImageShow_NeedAddJsStr);
            //###mX5webview.loadUrl(javascriptInterfaceHelper_openImageShow_NeedAddJsStr);



        }

        public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
            Log.d(TAG, "onPageStarted: " + url);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            webView.loadUrl(url);
            return true;
        }
    }
}
