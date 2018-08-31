package com.xiaoyang.netty.callbacks;

/**
 * @author WXY
 * @date 2018/8/31 10:52
 */

public interface Fetcher {
    void fetchData(FetcherCallback callback);
}
