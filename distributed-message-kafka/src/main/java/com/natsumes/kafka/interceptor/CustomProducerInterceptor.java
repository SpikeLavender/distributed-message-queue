package com.natsumes.kafka.interceptor;

import com.natsumes.kafka.entity.User;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hetengjiao
 */
@Component
public class CustomProducerInterceptor implements ProducerInterceptor<String, User> {
    @Override
    public ProducerRecord<String, User> onSend(ProducerRecord<String, User> record) {
        String key = record.key();
        String newKey = "prefix-" + key;
        return new ProducerRecord<>(record.topic(), newKey, record.value());
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
