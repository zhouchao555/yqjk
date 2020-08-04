package com.yhsl.business.demo.controller;


import com.yhsl.common.kit.RedisKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhouchao
 * @since 2019-12-11
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisKit redisKit;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/exist")
    public Object exist(String id){
        //查询缓存中是否存在
        boolean hasKey = redisKit.hasKey(id);
        return hasKey;
    }

    @RequestMapping(value = "/get")
    public Object get(String id){
        return redisTemplate.opsForValue().get(id);
    }

    @RequestMapping(value = "/hmget")
    public Object hmget(String id){
        Map<String, Object> map =  redisKit.hmget(id);
        return map;
    }


    @RequestMapping(value = "/set")
    public Object set(){
        redisKit.set("name","zhuzhu");
        return "ok";
    }

    @RequestMapping(value = "/hmset")
    public Object hmset(){
        Map<String,Object> testMap = new HashMap();
        testMap.put("name","666");
        testMap.put("age",27);
        testMap.put("class","1");
        redisKit.hmset("huhn", testMap);
        return "ok";
    }
}
