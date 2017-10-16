package com.bawei.atry.network;


import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;

/**
 * Created by 284385 on 2015/11/28.
 */
public class CacheUtils {
    /**
     * 设置缓存目录
     * @param file
     */
    public static void setCacheDir(File file,OkHttpClient client){
        //设置缓存大小
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        if (!file.exists()) {
            file.mkdirs();
        }
        Cache cache = new Cache(file, cacheSize);
        client.setCache(cache);
    }

    /**
     * 设置Http头
     * @param url
     * @param header
     * @return
     */
    public static Request setHeader(String url,String header){
        //这里的header可以为"no-cache"、"only-if-cached"等。
        //如果为"no-cache"则表示为强制使用网络
        //如果为"only-if-cached"则表示为强制使用缓存响应
        //当然还可以配置缓存过期时间。
        Request request = new Request.Builder()
                .header("Cache-Control", header)
                .url(url)
                .build();
        return request;
    }

}
