package com.bawei.atry.network;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ResultCallback<T> {
    public Type mType;
    public ResultCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
    	//获取带有泛型的父类
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        //获取泛型
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized
                .getActualTypeArguments()[0]);
    }

    public abstract void onError(Request request, Exception e);

    public abstract void onResponse(T response);
}
