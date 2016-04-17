package com.example.steven.fzxyactivity.common.util;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by Steven on 2016/3/26.
 */
public class OkUtils {

    public static void post(String url, Map<String,String> map, Callback callback){
        OkHttpUtils.post().url(url).params(map).build().execute(callback);
    }

    public static void get(){

    }
}
