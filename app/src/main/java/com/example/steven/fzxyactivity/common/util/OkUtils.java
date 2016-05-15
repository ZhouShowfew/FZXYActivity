package com.example.steven.fzxyactivity.common.util;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

/**
 * Created by Steven on 2016/3/26.
 * OkHttp工具类
 */
public class OkUtils {

    public static void post(String url, Map<String,String> map, StringCallback callback){
        OkHttpUtils.post().url(url).params(map).build().execute(callback);
    }

    public static void get(String url,Map<String,String> map,StringCallback callback){
        OkHttpUtils.get().url(url).params(map).build().execute(callback);
    }

    public static void get(String url,StringCallback callback){
        OkHttpUtils.get().url(url).build().execute(callback);
    }
}
