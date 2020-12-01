package com.natsumes.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hetengjiao
 */
@Data
@AllArgsConstructor
public class User {
    private Integer userId;

    private String username;
}
