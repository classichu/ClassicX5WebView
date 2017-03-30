package com.classichu.classicx5webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.classichu.x5webview.helper.ClassicX5WebViewHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClassicX5WebViewHelper.initFirstOnApp(this);
        //
        findViewById(R.id.id_dsada).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassicX5WebViewHelper.setDataAndToImageShow(MainActivity.this,"");
        }
        });
    }
}
