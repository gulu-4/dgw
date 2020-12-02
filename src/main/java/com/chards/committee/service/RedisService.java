package com.chards.committee.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    public RedisTemplate redisTemplate;

    public <T> T getStringValue(Object key, Class<T> beanClass) {
        if (key == null) return null;
        Object o = redisTemplate.opsForValue().get(key);
        return o == null ? null : JSON.parseObject(o.toString(), beanClass);
    }
}
