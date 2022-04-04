package cn.mryin.driveexam.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MrYin
 * @date 2021/12/23
 */
@ResponseBody
@RestController

public class Test {

   @Autowired
   StringRedisTemplate stringRedisTemplate;

   @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("/testRedis")
    public  String test(String name){
        int[] arr ={1};
        for (int i = 0; i < 10; i++) {
            System.out.println(arr[i]);
        }
        return null;
    }
}
