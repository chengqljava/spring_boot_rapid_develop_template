package com.hwsafe.template.controller.mq_demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MqProducerController {
    // @Autowired
    private Producer producer;

    // Direct
    @RequestMapping("/mq/producer/sendDirect")
    public void sendDirectMsg() {
        producer.sendDirectMsg("cord",
                String.valueOf(System.currentTimeMillis()));
    }

    // Topic
    @RequestMapping("/mq/producer/sendTopic")
    public void sendtopicMsg() {
        producer.sendExchangeMsg("topic-exchange", "org.cord.test",
                "topic-exchange hello world");
    }

    // Fanout
    @RequestMapping("/mq/producer/sendFanout")
    public void sendFanoutMsg() {
        producer.sendExchangeMsg("fanout-exchange", "abcdefg",
                String.valueOf(System.currentTimeMillis()));
    }

    // Headers
    @RequestMapping("/mq/producer/sendHeader")
    public void sendHeadersMsg() {
        Map<String, Object> map = new HashMap<>();
        map.put("First", "A");
        producer.sendHeadersMsg("headers-exchange",
                "headers-exchange hello word", map);
    }
}
