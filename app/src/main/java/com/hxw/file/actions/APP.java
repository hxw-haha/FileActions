package com.hxw.file.actions;

import android.app.Application;

import com.hxw.file.http.HttpFactory;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/5/18</p>
 * <p>更改时间：2021/5/18</p>
 * <p>版本号：1</p>
 */
public class APP extends Application {

//    public static final String BASE_URL = "http://172.20.10.6:9090";
    public static final String BASE_URL = "http://10.20.180.13:9083/fileAction/";

    @Override
    public void onCreate() {
        super.onCreate();
        HttpFactory.getInstance().init(new HttpFactory.HttpConfig(BASE_URL));
    }
}
