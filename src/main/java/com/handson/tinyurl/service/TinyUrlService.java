package com.handson.tinyurl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.tinyurl.model.ClickData;
import com.handson.tinyurl.model.NewTinyRequest;
import com.handson.tinyurl.model.User;
import com.handson.tinyurl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Random;

import static com.handson.tinyurl.model.UserBuilder.anUser;

@Service
public class TinyUrlService {

    private static final int MAX_RETRIES = 6;
    private static final int TINY_LENGTH = 6;

    @Autowired
    private UserService userService;

    @Autowired
    Redis redis;

    Random random = new Random();

    @Autowired
    ObjectMapper om;

    @Value("${base.url}")
    String baseUrl;

    public String generateTinyUrl(NewTinyRequest request) throws JsonProcessingException {
        String tinyCode = generateTinyCode();
        int i = 0;
        while (!redis.set(tinyCode, om.writeValueAsString(request)) && i < MAX_RETRIES) {
            tinyCode = generateTinyCode();
            i++;
        }
        if (i == MAX_RETRIES) throw new RuntimeException("SPACE IS FULL");

        userService.insertNewTiny(request.getUserName(), tinyCode);
        return baseUrl + tinyCode + "/";
    }

    public ModelAndView getTinyUrl(String tiny) throws JsonProcessingException {
        Object tinyRequestStr = redis.get(tiny);
        NewTinyRequest tinyRequest = om.readValue(tinyRequestStr.toString(),NewTinyRequest.class);

        userService.increaseMongoField(tinyRequest.getUserName(), "shorts."+tiny+".clicks."+LocalDateTime.now().getMonth());
        userService.increaseMongoField(tinyRequest.getUserName(), "allUserClicks");

        if (tinyRequest.getLongUrl() != null) {
            return new ModelAndView("redirect:" + tinyRequest.getLongUrl());
        } else {
            throw new RuntimeException(tiny + " not found");
        }
    }

    private String generateTinyCode() {
        String charPool = "ABCDEFHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < TINY_LENGTH; i++) {
            res.append(charPool.charAt(random.nextInt(charPool.length())));
        }
        return res.toString();
    }

}
