package com.natsumes.kafka.service.impl;

import com.natsumes.kafka.entity.User;
import com.natsumes.kafka.service.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

/**
 * @author hetengjiao
 */
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {


    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, User> userKafkaTemplate;

    @Override
    public void syncSend(String message) {
        ListenableFuture<SendResult<String, String>> future =
                stringKafkaTemplate.send(new ProducerRecord<>("topic-natsumes-02", 0, "1", message));
        // 同步等待broker的响应
        try {

            SendResult<String, String> result = future.get();

            System.out.println(result.getRecordMetadata().topic() + "\t"
                    + result.getRecordMetadata().partition() + "\t"
                    + result.getRecordMetadata().offset() + "\t");

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void asyncSend(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>("topic-natsumes-02", 0, "3", message);
        ListenableFuture<SendResult<String, String>> future = stringKafkaTemplate.send(record);
        //添加回调，等待异步响应
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送失败: " + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("发送成功: " + result.getRecordMetadata().topic() + "\t"
                        + result.getRecordMetadata().partition() + "\t"
                        + result.getRecordMetadata().offset() + "\t");
            }
        });
    }

    @Override
    public void userSend(User user) {
        ProducerRecord<String, User> record = new ProducerRecord<>("topic-natsumes-04", user.getUsername(), user);
        ListenableFuture<SendResult<String, User>> future = userKafkaTemplate.send(record);
        //添加回调，等待异步响应
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送失败: " + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, User> result) {
                System.out.println("发送成功: " + result.getRecordMetadata().topic() + "\t"
                        + result.getRecordMetadata().partition() + "\t"
                        + result.getRecordMetadata().offset() + "\t");
            }
        });
    }
}
