package com.uestc.getthecourse.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.uestc.getthecourse.util.BloomFilterHelper;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;

@Service
public class RedisService {

    @Resource
    JedisPool jedisPool;

    /**
     * 往布隆过滤器里添加数据
     * 首先拿到对应的布隆过滤器hash计算的槽位置
     * 再将这些位置上置为true
     *
     * @param bloomFilterHelper
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, KeyPrefix prefix, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //原生方法会导致多次网络IO，使用PIPeLINE方法
            Pipeline pipe = jedis.pipelined();
            String realKey = prefix.getPrefix() + key;
            for (int i : offset) {
                pipe.setbit(realKey, i, true);
            }
            pipe.syncAndReturnAll();//一次性传送所有的数据
        } finally {
            returnToPoll(jedis);
        }
    }

    /**
     * 通过布隆过滤器判断是否存在对应的数据。
     * @param bloomFilterHelper
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */

    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, KeyPrefix prefix, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            for (int i : offset) {
                if (!jedis.getbit(realKey, i)) {
                    return false;
                }
            }
        } finally {
            returnToPoll(jedis);
        }
        return true;
    }


    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = jedis.get(prefix.getPrefix() + key);
            T t = stringToBean(str, clazz);
            return t;
        } finally {
            returnToPoll(jedis);
        }
    }

    public <T> Boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null || str.length() <= 0) {
                return false;
            }
            String realKey = prefix.getPrefix() + key;
            int second = prefix.expireSeconds();
            if (second <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, second, str);
            }
            return true;
        } finally {
            returnToPoll(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if (value == null) return null;
        Class<?> aClass = value.getClass();
        if (aClass == int.class || aClass == Integer.class) {
            return "" + value;
        } else if (aClass == String.class) {
            return (String) value;
        } else if (aClass == long.class || aClass == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }

    }

    private <T> T stringToBean(String str, Class<T> aClass) {
        if (str == null || str.length() <= 0 || aClass == null) return null;
        if (aClass == int.class || aClass == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (aClass == String.class) {
            return (T) str;
        } else if (aClass == long.class || aClass == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), aClass);
        }
    }

    private void returnToPoll(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
