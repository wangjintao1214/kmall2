package com.kgc.kmall.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shkstart
 * @create 2021-01-04 14:32
 */
@Configuration
public class RedisConfing {

    @Value("192.168.164.130")
    private String host;
    @Value("${spring.redis.port:6379}")
    private Integer port;
    @Value("${spring.redis.database:0}")
    private Integer database;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+host+":"+port);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
