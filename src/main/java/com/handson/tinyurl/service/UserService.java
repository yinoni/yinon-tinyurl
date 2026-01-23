package com.handson.tinyurl.service;

import com.handson.tinyurl.model.*;
import com.handson.tinyurl.repository.UserClickRepository;
import com.handson.tinyurl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.handson.tinyurl.model.UserBuilder.anUser;
import static com.handson.tinyurl.model.UserClick.UserClickBuilder.anUserClick;
import static com.handson.tinyurl.model.UserClickKey.UserClickKeyBuilder.anUserClickKey;
import static org.springframework.data.util.StreamUtils.createStreamFromIterator;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository; //For inserting new user or for action on full document

    @Autowired
    MongoTemplate mongoTemplate; //For specific field actions, like increase the clicks number

    @Autowired
    private UserClickRepository userClickRepository;

    public User getUser(String username){
        User user = userRepository.findFirstByUsername(username).orElseThrow(() ->
                new RuntimeException("User not found!"));

        return user;
    }

    public User createUser(String username) {
        if(userRepository.findFirstByUsername(username).isPresent())
            throw new RuntimeException("Username already exists");

        User user = anUser().withUsername(username).withAllUserClicks(0).build();

        return userRepository.save(user);
    }

    public void insertNewTiny(String username, String tinyurl) {
        ClickData clickData = new ClickData();
        Query query = Query.query(Criteria.where("username").is(username));

        Update update = new Update().set("shorts." + tinyurl, clickData); //Because the field shorts is Map we are doing set and not addToSet or push

        mongoTemplate.updateFirst(query, update, "users");
    }

    public void increaseMongoField(String username, String key) {
        try{
            Query query = Query.query(Criteria.where("username").is(username));
            Update update = new Update().inc(key, 1);
            mongoTemplate.updateFirst(query, update, "users");
        }
        catch (Exception e){
            System.out.println("The error is ===> " + e);
        }

    }

    public List<UserClickOut> getUserClicks(String name) {
        return createStreamFromIterator( userClickRepository.findByUserName(name).iterator())
                .map(UserClickOut::of)
                .collect(Collectors.toList());
    }

    public UserClick saveUserClick(String tiny, NewTinyRequest tinyRequest) {
        return userClickRepository.save(anUserClick().userClickKey(anUserClickKey().withUserName(tinyRequest.getUserName()).withClickTime(new Date()).build())
                .tiny(tiny).longUrl(tinyRequest.getLongUrl()).build());
    }

}
