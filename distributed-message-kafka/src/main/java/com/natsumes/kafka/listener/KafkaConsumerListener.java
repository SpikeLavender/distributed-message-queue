package com.natsumes.kafka.listener;

import com.natsumes.kafka.entity.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author hetengjiao
 */
@Component
public class KafkaConsumerListener {

    @KafkaListener(
                topics = "topic-natsumes-02",
                properties = {"value.deserializer=org.apache.kafka.common.serialization.StringDeserializer"}
            )
    public void onMessage(ConsumerRecord<String, String> record) {
        Optional<ConsumerRecord<String, String>> optional = Optional.ofNullable(record);
        if (optional.isPresent()) {
            System.out.println(record.topic() + "\t"
                    + record.partition() + "\t"
                    + record.offset() + "\t"
                    + record.key() + "\t"
                    + record.value());
        }
    }

    @KafkaListener(
                topics = "topic-natsumes-04",
                properties = {
                        "value.deserializer=com.natsumes.kafka.serializer.UserDeserializer",
                        ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG + "=com.natsumes.kafka.interceptor.CustomConsumerInterceptor"
                }
            )
    public void onUserMessage(ConsumerRecord<String, User> record) {
        Optional<ConsumerRecord<String, User>> optional = Optional.ofNullable(record);
        if (optional.isPresent()) {
            System.out.println("消费成功: " + record.topic() + "\t"
                    + record.partition() + "\t"
                    + record.offset() + "\t"
                    + record.key() + "\t"
                    + record.value());
        }
    }
}
