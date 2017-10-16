package com.bawei.atry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bawei.atry.network.OkhttpUtils;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private String path="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=1&size=10&offset=0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(){
            @Override
            public void run() {
                try {
                    Response response = OkhttpUtils.getSyn(path);
                    String string = response.body().string();
                    System.out.println(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
