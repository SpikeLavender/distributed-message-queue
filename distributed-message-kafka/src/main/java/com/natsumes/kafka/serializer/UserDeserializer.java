package com.natsumes.kafka.serializer;

import com.natsumes.kafka.entity.User;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @author hetengjiao
 */
@Component
public class UserDeserializer implements Deserializer<User> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public User deserialize(String topic, byte[] data) {
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data);
        buffer.flip();
        final int userId = buffer.getInt();
        final int usernameLen = buffer.getInt();
        String username = new String(data, 8, usernameLen);

        return new User(userId, username);
    }

    @Override
    public void close() {

    }
}
