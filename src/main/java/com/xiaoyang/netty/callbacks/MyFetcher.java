package com.xiaoyang.netty.callbacks;

import java.util.Date;

/**
 * @author WXY
 * @date 2018/8/31 11:11
 */

public class MyFetcher implements Fetcher{

    private final Date date;

    public MyFetcher(Date date) {
        this.date = date;
    }

    public void fetchData(FetcherCallback callback) {
        try {
            callback.onData(date);
        } catch (Exception e) {
            callback.onError(e);
            e.printStackTrace();
        }
    }
}
