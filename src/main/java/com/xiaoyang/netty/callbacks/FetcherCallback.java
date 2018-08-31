package com.xiaoyang.netty.callbacks;

import java.util.Date;

/**
 * @author WXY
 * @date 2018/8/31 10:53
 */

public interface FetcherCallback {

    void onData(Date date) throws Exception;
    void onError(Throwable cause);

}
