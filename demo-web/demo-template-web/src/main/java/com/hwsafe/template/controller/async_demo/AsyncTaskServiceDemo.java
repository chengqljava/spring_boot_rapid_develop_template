package com.hwsafe.template.controller.async_demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
public class AsyncTaskServiceDemo {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 声明为异步方法
     */
    @Async
    public void executeAsyncTask(int number) {
        System.out.println("异步方法" + number);
    }

    @Scheduled(fixedDelay = 5000)
    public void reportCurremtTime() {
        System.out.println("每隔5秒执行一次" + simpleDateFormat.format(new Date()));
    }

    @Scheduled(cron = "0 */1 * * * ? ")
    public void fixTimeExecution() {
        System.out.println("用 cron 0 */1 * * * ? 指每分钟执行-次 "
                + simpleDateFormat.format(new Date()));
    }

}
