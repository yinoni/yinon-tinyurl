package com.handson.tinyurl.controller;

import com.handson.tinyurl.service.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {
    @Autowired
    Redis redis;

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public Boolean set(@RequestParam String key,  @RequestParam String value) {
        return redis.set(key,value);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(@RequestParam String key) {
        try{
            return redis.get(key).toString();
        }
        catch (Exception e){
            return e.getMessage();
        }

    }

}
