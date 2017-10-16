package com.bawei.atry.network;


import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;

/**
 * 通过拦截器实现对requestbody的压缩。
 */
public class GZIPRequestInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest=chain.request();
        if(originalRequest.body()==null || originalRequest.header("Content-Encoding")!=null){
            return  chain.proceed(originalRequest);
        }

        Request compressRequest = originalRequest.newBuilder()
                .header("Content-Encoding", "gzip")
                .method(originalRequest.method(), gzip(originalRequest.body()))
                .build();

        return chain.proceed(compressRequest);
    }


    private RequestBody gzip(final RequestBody body){
        final Buffer buffer=new Buffer();
        try {
            body.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() throws IOException {
                return buffer.size();
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(buffer.snapshot());
            }
        };
    }
}
