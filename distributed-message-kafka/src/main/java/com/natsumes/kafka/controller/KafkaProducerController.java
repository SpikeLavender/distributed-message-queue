package com.natsumes.kafka.controller;

import com.natsumes.kafka.entity.User;
import com.natsumes.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hetengjiao
 */
@RestController
public class KafkaProducerController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @RequestMapping("send/sync/{message}")
    public String sendSync(@PathVariable String message) {
        kafkaProducerService.syncSend(message);
        return "success";
    }

    @RequestMapping("send/async/{message}")
    public String sendAsync(@PathVariable String message) {
        kafkaProducerService.asyncSend(message);
        return "success";
    }

    @PostMapping("send/user")
    public String sendUser(@RequestBody User user) {
        kafkaProducerService.userSend(user);
        return "success";
    }
}
