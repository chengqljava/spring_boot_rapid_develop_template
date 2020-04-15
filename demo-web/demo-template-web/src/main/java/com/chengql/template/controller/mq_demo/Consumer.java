package com.hwsafe.template.controller.mq_demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class Consumer {

    @RabbitListener(queues = "cord")
    // @RabbitListener(queues = "cord", containerFactory="myFactory")
    public void processMessage(String msg) {
        System.out.format("Receiving Message: -----[%s]----- \n.", msg);
    }
}