package com.natsumes.kafka.config;

import com.natsumes.kafka.entity.User;
import com.natsumes.kafka.serializer.UserSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hetengjiao
 */
@Configuration
public class KafkaProducerConfigure {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(16);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // 如果有多个，设置为多个全限定类名，并用逗号隔开
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "com.natsumes.kafka.interceptor.CustomProducerInterceptor");
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.natsumes.kafka.partitioner.MyPartitioner");
        return props;
    }

    @Bean
    public <K, V> ProducerFactory<K, V> producerFactory() {
        Map<String, Object> configs = producerConfigs();
        DefaultKafkaProducerFactory<K, V> factory = new DefaultKafkaProducerFactory<>(configs);
        factory.setTransactionIdPrefix("tx-");
        return factory;
    }

    @Bean
    public KafkaTemplate<String, String> stringKafkaTemplate() {
        DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(producerConfigs());
        factory.setKeySerializer(new StringSerializer());
        factory.setValueSerializer(new StringSerializer());
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, User> userKafkaTemplate() {
        DefaultKafkaProducerFactory<String, User> factory = new DefaultKafkaProducerFactory<>(producerConfigs());
        factory.setKeySerializer(new StringSerializer());
        factory.setValueSerializer(new UserSerializer());
        return new KafkaTemplate<>(factory);
    }

}
