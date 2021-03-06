package com.uestc.getthecourse.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

@Service
public class RedisPoolFactory {
    @Resource
    RedisConfig redisConfig;

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPollMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPollMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPollMaxWait() * 1000);
        JedisPool jp = new JedisPool(poolConfig,redisConfig.getHost(),redisConfig.getPort(),redisConfig.getTimeout() * 1000);
        return jp;
    }
}
