package com.example.learning.lab1;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController1 {
    @GetMapping("/hello1")
    public HelloResponse hello(){
        return new HelloResponse("Welcome to Hello Banking API");
    }

    @PostMapping("/hello1")
    public HelloResponse hello1(@RequestBody String name) {
        return new HelloResponse("Hello "+name+", Welcome to Hello Banking API");
    }
}

// get - Welcome to Hello Banking API
// post - name in body - Hello <name>, Welcome to Hello Banking App