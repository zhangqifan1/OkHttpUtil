package com.bawei.atry.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;

    /**
     * 初始化对象
     */
    private OkHttpClientManager() {

        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.networkInterceptors().add(new GZIPRequestInterceptor());
        mOkHttpClient.networkInterceptors().add(new CacheInterceptor());
        mOkHttpClient.networkInterceptors().add(new LoggingInterceptor());
        // cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null,
                CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    /**
     * @Title: getInstance
     * @Description: TODO(获取OkHttpClientManager对象)
     * @param @return    设定文件
     * @return OkHttpClientManager    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * @Title: _getSyn
     * @Description: TODO(同步的Get请求)
     * @param @param url
     * @param @return
     * @param @throws IOException    设定文件
     * @return Response    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public Response _getSyn(String url) throws IOException {
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    /**
     * @param <T>
     * @Title: _getAsyn
     * @Description: TODO(异步的get请求)
     * @param @param url
     * @param @param callback    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public <T> void _getAsyn(String url, final ResultCallback<T> callback) {
        final Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }

    /**
     * @Title: _postSyn
     * @Description: TODO(同步的Post请求)
     * @param @param url
     * @param @param params
     * @param @return
     * @param @throws IOException    设定文件
     * @return Response    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public Response _postSyn(String url, Map<String, String> params) throws IOException {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * @param <T>
     * @Title: _postAsyn
     * @Description: TODO(异步的post请求)
     * @param @param url
     * @param @param callback
     * @param @param params    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public <T> void _postAsyn(String url, Map<String, String> params,
                              final ResultCallback<T> callback) {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    /**
     * @Title: _UploadPostSyn
     * @Description: TODO(同步基于post的多文件上传)
     * @param @param url
     * @param @param files
     * @param @param fileKeys
     * @param @param params
     * @param @return
     * @param @throws IOException    设定文件
     * @return Response    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public Response _UploadPostSyn(String url, Map<String, String> params,File[] files, String[] fileKeys
    ) throws IOException {
        Param[] paramsArr = map2Params(params);
        Request request = buildMultipartFormRequest(url, files, fileKeys,
                paramsArr);
        return mOkHttpClient.newCall(request).execute();
    }


    /**
     * @Title: _UploadPostSyn
     * @Description: TODO(同步基于post的单文件上传)
     * @param @param url
     * @param @param file
     * @param @param fileKey
     * @param @param params
     * @param @return
     * @param @throws IOException    设定文件
     * @return Response    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public Response _UploadPostSyn(String url, Map<String, String> params,File file, String fileKey
    ) throws IOException {
        Param[] paramsArr = map2Params(params);
        Request request = buildMultipartFormRequest(url, new File[]{file},
                new String[]{fileKey}, paramsArr);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * @param <T>
     * @Title: _UploadPostAsyn
     * @Description: TODO(异步的post多文件上传)
     * @param @param url
     * @param @param callback
     * @param @param files
     * @param @param fileKeys
     * @param @param params
     * @param @throws IOException    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public <T> void _UploadPostAsyn(String url, Map<String, String> params, File[] files, String[] fileKeys,ResultCallback<T> callback)
            throws IOException {
        Param[] paramsArr = map2Params(params);
        Request request = buildMultipartFormRequest(url, files, fileKeys,
                paramsArr);
        deliveryResult(callback, request);
    }


    /**
     * @param <T>
     * @Title: _UploadPostAsyn
     * @Description: TODO(异步基于post的单文件上传)
     * @param @param url
     * @param @param callback
     * @param @param file
     * @param @param fileKey
     * @param @param params
     * @param @throws IOException    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public <T> void _UploadPostAsyn(String url, Map<String, String> params,
                                    File file, String fileKey,ResultCallback<T> callback ) throws IOException {
        Param[] paramsArr = map2Params(params);
        Request request = buildMultipartFormRequest(url, new File[]{file},
                new String[]{fileKey}, paramsArr);
        deliveryResult(callback, request);
    }

    /**
     * @param <T>
     * @Title: _downloadAsyn
     * @Description: TODO(异步下载文件)
     * @param @param url
     * @param @param destFileDir
     * @param @param callback    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public <T> void _downloadAsyn(final String url, final String destFileDir,
                                  final ResultCallback<T> callback) {
        final Request request = new Request.Builder().url(url).build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    /**
                     *   如果下载文件成功，第一个参数为文件的绝对路径
                     */
                    sendSuccessResultCallback(file.getAbsolutePath(), callback);
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }

            }
        });
    }


    /**
     * @Title: _displayImage
     * @Description: TODO(图片的异步加载)
     * @param @param view
     * @param @param url
     * @param @param errorResId    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public void _displayImage(final ImageView view, final String url,
                              final int errorResId) {
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                setErrorResId(view, errorResId);
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                try {
                    is = response.body().byteStream();
                    ImageUtils.ImageSize actualImageSize = ImageUtils
                            .getImageSize(is);
                    ImageUtils.ImageSize imageViewSize = ImageUtils
                            .getImageViewSize(view);
                    int inSampleSize = ImageUtils.calculateInSampleSize(
                            actualImageSize, imageViewSize);
                    try {
                        is.reset();
                    } catch (IOException e) {
                        response = _getSyn(url);
                        is = response.body().byteStream();
                    }

                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = false;
                    ops.inSampleSize = inSampleSize;
                    final Bitmap bm = BitmapFactory.decodeStream(is, null, ops);
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(bm);
                        }
                    });
                } catch (Exception e) {
                    setErrorResId(view, errorResId);

                } finally {
                    if (is != null)
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        });

    }


    /**
     * @Title: getFileName
     * @Description: TODO(获取文件名)
     * @param @param path
     * @param @return    设定文件
     * @return String    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1,
                path.length());
    }



    /**
     * @Title: setErrorResId
     * @Description: TODO(图片加载出错时的默认图片)
     * @param @param view
     * @param @param errorResId    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    private void setErrorResId(final ImageView view, final int errorResId) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                view.setImageResource(errorResId);
            }
        });
    }

    /**
     * @Title: buildMultipartFormRequest
     * @Description: TODO(文件上传的封装)
     * @param @param url
     * @param @param files
     * @param @param fileKeys
     * @param @param params
     * @param @return    设定文件
     * @return Request    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Param[] params) {
        params = validateParam(params);

        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);

        for (Param param : params) {
            builder.addPart(
                    Headers.of("Content-Disposition", "form-data; name=\""
                            + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(
                        MediaType.parse(guessMimeType(fileName)), file);
                // TODO 根据文件名设置contentType
                builder.addPart(
                        Headers.of("Content-Disposition", "form-data; name=\""
                                + fileKeys[i] + "\"; filename=\"" + fileName
                                + "\""), fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * @Title: guessMimeType
     * @Description: TODO(获取文件的mime类型)
     * @param @param name
     * @param @return    设定文件
     * @return String    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public String guessMimeType(String name) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(name);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * @Title: validateParam
     * @Description: TODO(对Param[]的校验)
     * @param @param params
     * @param @return    设定文件
     * @return Param[]    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else
            return params;
    }

    /**
     * @Title: map2Params
     * @Description: TODO(对参数的转换)
     * @param @param params
     * @param @return    设定文件
     * @return Param[]    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    private Param[] map2Params(Map<String, String> params) {
        if (params == null)
            return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    /**
     * @param <T>
     * @Title: deliveryResult
     * @Description: TODO(对request的处理)
     * @param @param callback
     * @param @param request    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    private <T> void deliveryResult(final ResultCallback<T> callback,
                                    Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(final Response response) {
                try {
                    final String string = response.body().string();
                    if (callback.mType == String.class) {
                        sendSuccessResultCallback(string, callback);
                    } else {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }

                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (JsonParseException e) {// Json解析的错误
                    sendFailedStringCallback(response.request(), e, callback);
                }

            }
        });
    }

    /**
     * @param <T>
     * @Title: sendFailedStringCallback
     * @Description: TODO(失败的回调)
     * @param @param request
     * @param @param e
     * @param @param callback    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    private <T> void sendFailedStringCallback(final Request request,
                                              final Exception e, final ResultCallback<T> callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    /**
     * @param <T>
     * @Title: sendSuccessResultCallback
     * @Description: TODO(成功的回调)
     * @param @param object
     * @param @param callback    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    private <T> void sendSuccessResultCallback(final Object object,
                                               final ResultCallback<T> callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse((T) object);
                }
            }
        });
    }

    /**
     * @Title: buildPostRequest
     * @Description: TODO(post方式提交参数封装)
     * @param @param url
     * @param @param params
     * @param @return    设定文件
     * @return Request    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

}

