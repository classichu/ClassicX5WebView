package com.classichu.x5webview;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dialogview.ui.ClassicDialogFragment;
import com.classichu.x5webview.helper.JavascriptInterfaceHelper;
import com.classichu.x5webview.ui.ClassicX5WebView;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebView;

public class ClassicX5WebViewActivity extends AppCompatActivity {

    private static final String TAG = "ClassicX5WebViewActivit";
    private String mUrl = "http://baidu.com";
    private ClassicX5WebView mClassicX5WebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_x5webview);

        /**
         * loadUrl
         */
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (!TextUtils.isEmpty(bundle.getString("url"))) {
                mUrl = bundle.getString("url");
            }
            mIsToolbarTitleCenter = bundle.getBoolean("isTitleCenter");


        }
        initToolbar();
        mClassicX5WebView = (ClassicX5WebView) findViewById(R.id.id_classic_webview);

        /**
         * 其他判断方法有问题  注意测试
         */
        if (mClassicX5WebView.getX5WebViewExtension() == null) {
            Log.d(TAG, "onCreate: X5 内核未真正加载！");
        }
        /**
         * javascriptInterfaceHelper 别名要和js里面的一致
         */
        mClassicX5WebView.addJavascriptInterface(new JavascriptInterfaceHelper(this), "javascriptInterfaceHelper");
        mClassicX5WebView.setX5WebViewDelegate(new ClassicX5WebView.X5WebViewDelegate() {
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, int defaultValue, JsResult jsResult) {
                DialogManager.showTipDialog(ClassicX5WebViewActivity.this, "提示", message, null);
                return super.onJsAlert(webView, url, message, defaultValue, jsResult);
            }

            @Override
            public boolean onJsConfirm(WebView webView, String url, String message, final JsResult jsResult) {
                DialogManager.showClassicDialog(ClassicX5WebViewActivity.this, "提示", message,
                        new ClassicDialogFragment.OnBtnClickListener() {
                            @Override
                            public void onBtnClickOk(DialogInterface dialogInterface) {
                                super.onBtnClickOk(dialogInterface);
                                jsResult.confirm();
                            }

                            @Override
                            public void onBtnClickCancel(DialogInterface dialogInterface) {
                                super.onBtnClickCancel(dialogInterface);
                                jsResult.cancel();
                            }
                        });
                // return super.onJsConfirm(webView, url, message, jsResult);
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult jsPromptResult) {
                return super.onJsPrompt(webView, url, message, defaultValue, jsPromptResult);
            }

            @Override
            public boolean onJsTimeout() {
                // DialogManager.hideLoadingDialog();
                return super.onJsTimeout();
            }

            @Override
            public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
                super.onPageStarted(webView, url, bitmap);
                DialogManager.showLoadingDialog(ClassicX5WebViewActivity.this, true);
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                DialogManager.hideLoadingDialog();
            }

            @Override
            public void onReceivedTitle(WebView webView, String title) {
                super.onReceivedTitle(webView, title);
                setToolbarTitle(title);
            }
        });

        //
        mClassicX5WebView.loadUrl(mUrl);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        if (mToolbar == null) {
            return;
        }
        mToolbarTitleView = (TextView) mToolbar.findViewById(R.id.id_toolbar_title);

        /**
         * setToolbarTitle
         */
        this.setToolbarTitle(mToolbar.getTitle() != null ? mToolbar.getTitle().toString() : "");

        mToolbar.setVisibility(View.VISIBLE);
        //替换ActionBar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //必须设置在setSupportActionBar(mToolbar);后才有效
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前aty
                finish();
            }
        });
    }

    private TextView mToolbarTitleView;
    private Toolbar mToolbar;
    private boolean mIsToolbarTitleCenter;

    protected void setToolbarTitle(String string) {
        if (mIsToolbarTitleCenter) {
            if (mToolbarTitleView != null) {
                mToolbarTitleView.setText(string);
                mToolbar.setTitle("");
                mToolbarTitleView.setVisibility(View.VISIBLE);
            }
        } else {
            if (mToolbarTitleView != null) {
                mToolbarTitleView.setText("");
                mToolbar.setTitle(string);
                mToolbarTitleView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mClassicX5WebView.canGoBack()) {
            mClassicX5WebView.goBack();// 返回前一个页面
        } else {
            mClassicX5WebView.destroy();
            super.onBackPressed();
        }
    }
}
