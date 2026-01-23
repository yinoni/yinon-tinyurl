package com.handson.tinyurl.controller;

import com.handson.tinyurl.model.User;
import com.handson.tinyurl.model.UserClickOut;
import com.handson.tinyurl.model.UserIn;
import com.handson.tinyurl.repository.UserClickRepository;
import com.handson.tinyurl.repository.UserRepository;
import com.handson.tinyurl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.util.StreamUtils.createStreamFromIterator;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<User> save(@RequestBody UserIn userIn) {
        return ResponseEntity.ok(userService.createUser(userIn.getUsername()));
    }

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }


    @RequestMapping(value = "/{name}/clicks", method = RequestMethod.GET)
    public ResponseEntity<?> getUserClicks(@RequestParam String name) {
        return ResponseEntity.ok(userService.getUserClicks(name));
    }

}
