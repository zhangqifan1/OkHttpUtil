package com.bawei.atry.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by 张祺钒
 * on2017/9/7.
 * function:
 * 自定义的缓存拦截器 如果服务器没有给文件在响应头中定义缓存标签,
 *                  那么我们在拦截器中手动的给响应头加上标签
 *
 *1.自定义一个类实现Interceptor
 *2.intercept中做逻辑操作
 */

public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        return response.newBuilder()
                //设置缓存标签,及60S秒的时长
                .header("Cache-Control","max-age=60")
                .build();
    }
}