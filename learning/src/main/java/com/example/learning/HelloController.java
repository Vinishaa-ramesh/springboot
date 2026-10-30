package com.example.learning;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello/{name}")
    public HelloResponse helloParam(@PathVariable String name) {
        return new HelloResponse("Hello, "+name);
    }

    @GetMapping("/hello")
    public HelloResponse hello() {
        return new HelloResponse("Hello World!");
    }

    @PostMapping("/hello")
    public String helloPost(@RequestBody String name) {
        return "Hello, " + name + "!";
    }
}
