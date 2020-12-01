package com.natsumes.kafka.serializer;

import com.natsumes.kafka.entity.User;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author hetengjiao
 */
@Component
public class UserSerializer implements Serializer<User> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, User data) {

        if (data == null) {
            return null;
        }

        final Integer userId = data.getUserId();
        final String username = data.getUsername();
        if (userId != null) {
            if (username != null) {
                final byte[] bytes = username.getBytes(StandardCharsets.UTF_8);
                int length = bytes.length;
                // 第一个4个字节用于存储userId的值
                // 第二个4个字节用于存储username字节数组的长度int值
                // 第三个长度用于存储username序列化之后的字节数组
                ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + length);
                buffer.putInt(userId);
                buffer.putInt(length);
                buffer.put(bytes);
                return buffer.array();
            }
        }
        return null;
    }

    @Override
    public void close() {

    }
}
