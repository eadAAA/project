package com.microservice.book.api.controller;

import com.microservice.book.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class Hystrix {
    @Autowired
    BookService bookservice;

    @RequestMapping(value = "/getGroupDetails/{group}", method = RequestMethod.GET)
    public String getUsers(@PathVariable String group) {
        return bookservice.callBookService(group);
    }
}
