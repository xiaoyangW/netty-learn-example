package com.xiaoyang.netty.callbacks;

import java.util.Date;

/**
 * @author WXY
 * @date 2018/8/31 10:50
 */

public class Worker {

    public void doWorker(){
        Fetcher fetcher = new MyFetcher(new Date());
        fetcher.fetchData(new FetcherCallback() {
            public void onData(Date date) throws Exception {
                System.out.println(date);
            }

            public void onError(Throwable cause) {
                System.out.println("error:"+cause.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.doWorker();
    }

}
