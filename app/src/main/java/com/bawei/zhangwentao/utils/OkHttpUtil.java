package com.bawei.zhangwentao.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * 作者：张文涛
 * 功能：网络请求框架封装
 * 时间：2019-11-5 11:09:34
 */

public class OkHttpUtil {
    // 单利模式
    private static OkHttpUtil okHttpUtil = null;
    private OkHttpUtil(){
        Interceptor interceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                return null;
            }
        };
       new OkHttpClient.Builder()
               .readTimeout(5, TimeUnit.SECONDS)
               .writeTimeout(5,TimeUnit.SECONDS)
               .connectTimeout(5, TimeUnit.SECONDS)
               .addInterceptor(interceptor)
               .build();
    }
    public static OkHttpUtil getInstance() {
        if (okHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpUtil == null) {
                    okHttpUtil = new OkHttpUtil();
                }
            }
        }
        return okHttpUtil;
    }

    public static Boolean isNetWorkConnection(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }


}
