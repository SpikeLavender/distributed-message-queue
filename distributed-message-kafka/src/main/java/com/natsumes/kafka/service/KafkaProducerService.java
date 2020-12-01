package com.natsumes.kafka.service;

import com.natsumes.kafka.entity.User;

/**
 * @author hetengjiao
 */
public interface KafkaProducerService {

    void syncSend(String message);

    void asyncSend(String message);

    void userSend(User user);
}
