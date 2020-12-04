package com.natsumes.kafka.interceptor;

import com.natsumes.kafka.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hetengjiao
 */
@Component
@Slf4j
public class CustomConsumerInterceptor implements ConsumerInterceptor<String, User> {
    @Override
    public ConsumerRecords<String, User> onConsume(ConsumerRecords<String, User> records) {
        System.out.println("consumer num = " + records.count());
        return records;
    }

    @Override
    public void close() {

    }

    @Override
    public void onCommit(Map offsets) {

    }

    @Override
    public void configure(Map<String, ?> configs) {
        configs.forEach((k,v) -> log.info("{} = {}", k, v));
    }
}
