package com.bawei.atry.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

class LoggingInterceptor implements Interceptor {
  @Override public Response intercept(Interceptor.Chain chain) throws IOException {
    Request request = chain.request();


    long t1 = System.nanoTime();

    System.out.println(" request  = " + String.format("Sending request %s on %s%n%s",
            request.url(), chain.connection(), request.headers()));
    Response response = chain.proceed(request);

    long t2 = System.nanoTime();

    //得出请求网络,到得到结果,中间消耗了多长时间
    System.out.println("结果:  " + String.format("Received response for %s in %.1fms%n%s",
            response.request().url(), (t2 - t1) / 1e6d, response.headers()));
    return response;
  }
}