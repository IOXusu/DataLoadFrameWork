package com.xusu.dataloadlibrary;

import android.content.Context;
import android.os.Environment;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 负责缓存数据的加载与保存，
 * 1.定义数据缓存目录
 * Created by lxj on 2016/5/23.
 */
public class CacheLoader {

    private static Context context;

    //定义缓存文件的目录
    //最终拼接的目录为：/mnt/sdcard/shell/emulated/0/包名/cache
    private static String CACHE_DIR = Environment.getExternalStorageDirectory()
            + File.separator + context.getPackageName()
            + File.separator + "cache";

    private CacheLoader(Context context){
        //初始化缓存目录
        File cacheDir = new File(CACHE_DIR);
        if(!cacheDir.exists()){
            //创建目录结构
            cacheDir.mkdirs();
        }
        this.context = context;
    }
    private static CacheLoader mInstance = new CacheLoader(context);
    public static CacheLoader getInstance(){
        return mInstance;
    }

    /**
     * 根据url读取本地缓存数据
     * @param url
     * @return
     */
    public String getCacheData(String url) {
        return "cache";
    }

    /**
     * 缓存数据到本地文件
     * @param url
     * @param result
     */
    public void saveCacheData(String url, String result) {
        //1.构建缓存文件,缓存文件的文件名称需要生层
        File file = buildCacheFile(url);
        //2.将result数据存入file中
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write(result);
            //刷一下
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建缓存文件
     * @return
     */
    private File buildCacheFile(String url) {
        //http://www.baidu.com/1/s/333?index=1
        String name = url.substring(url.lastIndexOf("?"), url.length());
        return new File(CACHE_DIR,"temp");
    }
}
