package com.bawei.atry.network;

import android.widget.ImageView;

import com.squareup.okhttp.Response;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 工具类
 */
public class OkhttpUtils {

    /**
     * @Title: getSyn
     * @Description: TODO(同步get请求)
     * @param @param url
     * @param @return
     * @param @throws IOException    设定文件
     * @return Response    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static Response getSyn(String url) throws IOException {
        return OkHttpClientManager.getInstance()._getSyn(url);
    }

    /**
     * @param <T>
     * @Title: getAsyn
     * @Description: TODO(异步的get请求)
     * @param @param url
     * @param @param callback    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static <T> void getAsyn(String url,ResultCallback<T> callback){
        OkHttpClientManager.getInstance()._getAsyn(url,callback);
    }


    /**
     * @Title: postSyn
     * @Description: TODO(同步的Post请求)
     * @param @param url
     * @param @param params
     * @param @return
     * @param @throws IOException    设定文件
     * @return Response    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static Response postSyn(String url, Map<String, String> params) throws IOException {
        return OkHttpClientManager.getInstance()._postSyn(url,params);
    }

    /**
     * @param <T>
     * @Title: postAsyn
     * @Description: TODO(异步的post请求)
     * @param @param url
     * @param @param params
     * @param @param callback    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static <T> void postAsyn(String url, Map<String, String> params,
                                    ResultCallback<T> callback){
        OkHttpClientManager.getInstance()._postAsyn(url,params,callback);
    }


    /**
     * @Title: UploadPostSyn
     * @Description: TODO(异步的post请求)
     * @param @param url
     * @param @param params
     * @param @param files
     * @param @param fileKeys
     * @param @return
     * @param @throws IOException    设定文件
     * @return Response    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static Response UploadPostSyn(String url, Map<String, String> params,File[] files,
                                         String[] fileKeys) throws IOException {
        return OkHttpClientManager.getInstance()._UploadPostSyn(url,params,files,fileKeys);
    }

    /**
     * @Title: UploadPostSyn
     * @Description: TODO(同步基于post的单文件上传)
     * @param @param url
     * @param @param params
     * @param @param file
     * @param @param fileKey
     * @param @return
     * @param @throws IOException    设定文件
     * @return Response    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static Response UploadPostSyn(String url, Map<String, String> params,File file,
                                         String fileKey) throws IOException {
        return OkHttpClientManager.getInstance()._UploadPostSyn(url,params,file,fileKey);
    }

    /**
     * @param <T>
     * @Title: UploadPostAsyn
     * @Description: TODO(异步post多文件上传)
     * @param @param url
     * @param @param params
     * @param @param files
     * @param @param fileKeys
     * @param @param callback
     * @param @throws IOException    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static <T> void UploadPostAsyn(String url, Map<String, String> params, File[] files, String[] fileKeys,
                                          ResultCallback<T> callback)throws IOException{
        OkHttpClientManager.getInstance()._UploadPostAsyn(url, params, files, fileKeys, callback);
    }

    /**
     * @param <T>
     * @Title: UploadPostAsyn
     * @Description: TODO(异步post单文件上传)
     * @param @param url
     * @param @param params
     * @param @param file
     * @param @param fileKey
     * @param @param callback
     * @param @throws IOException    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static <T> void UploadPostAsyn(String url, Map<String, String> params,
                                          File file, String fileKey,ResultCallback<T> callback ) throws IOException {
        OkHttpClientManager.getInstance()._UploadPostAsyn(url, params, file, fileKey, callback);
    }

    /**
     * @param <T>
     * @Title: downloadAsyn
     * @Description: TODO(异步下载文件)
     * @param @param url
     * @param @param destFileDir
     * @param @param callback    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static <T> void downloadAsyn(String url, String destFileDir,
                                        ResultCallback<T> callback){
        OkHttpClientManager.getInstance()._downloadAsyn(url, destFileDir, callback);
    }


    /**
     * @Title: displayImage
     * @Description: TODO(图片的异步加载)
     * @param @param view
     * @param @param url
     * @param @param errorResId    设定文件
     * @return void    返回类型
     * @author: minliang@deppon.com | 284385
     * @throws
     */
    public static void displayImage(ImageView view,String url,int errorResId){
        OkHttpClientManager.getInstance()._displayImage(view,url,errorResId);
    }
}
