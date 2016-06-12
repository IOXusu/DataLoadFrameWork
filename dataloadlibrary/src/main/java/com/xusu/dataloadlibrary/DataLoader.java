package com.xusu.dataloadlibrary;


import com.google.gson.Gson;

/**
 * 数据加载器，负责缓存数据和网络数据的调度，
 * 逻辑是：
 * 1.优先从网络加载，如果为空，则从缓存加载，
 * 2.如果网络数据不为空，则更新数据到本地
 * 3.拿到数据后，负责将json数据解析为bean对象
 * Created by lxj on 2016/5/23.
 */
public class DataLoader {
    private DataLoader(){}
    private static DataLoader mInstance = new DataLoader();
    public static DataLoader getInstance(){
        return mInstance;
    }

    /**
     * 加载数据
     * @param url
     * @return
     */
    public <T> T loadBeanData(String url,Class<T> clazz){
        //1.优先从网络加载数据
        String result = HttpLoader.getInstance().executeGet(url);
        //2.如果网络为空，则尝试加载本地缓存数据
        if(result==null){
            result = CacheLoader.getInstance().getCacheData(url);
        }else {
            //3.如果网络数据不为空，则将数据更新至本地缓存;
            CacheLoader.getInstance().saveCacheData(url,result);
        }

        //4.如果result的json数据不是空，则进行解析后返回
        if(result!=null){
            //使用Gson解析json
            return new Gson().fromJson(result,clazz);
        }else {
            return null;
        }
    }
}
