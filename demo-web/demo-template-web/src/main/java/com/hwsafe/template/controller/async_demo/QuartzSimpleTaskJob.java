package com.hwsafe.template.controller.async_demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component("quartzSimpleTaskJob")
public class QuartzSimpleTaskJob {
    @Scheduled(fixedRate = 5000)
    public void fiveSecond() {
        System.err.println("quartz 定时任务不带参数 5秒一次");
    }

}
