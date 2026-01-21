package com.handson.tinyurl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.tinyurl.model.NewTinyRequest;
import com.handson.tinyurl.service.Redis;
import com.handson.tinyurl.service.TinyUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@RestController
public class AppController {

    @Autowired
    private TinyUrlService tinyUrlService;

    @RequestMapping(value = "/tiny", method = RequestMethod.POST)
    public String generate(@RequestBody NewTinyRequest request) throws JsonProcessingException {
        return tinyUrlService.generateTinyUrl(request);
    }

    @RequestMapping(value = "/{tiny}/", method = RequestMethod.GET)
    public ModelAndView getTiny(@PathVariable String tiny) throws JsonProcessingException {
        return tinyUrlService.getTinyUrl(tiny);
    }

}
