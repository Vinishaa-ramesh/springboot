package com.example.learning.lab1;

public class HelloResponse {
    private String message;

    HelloResponse(String name) {
        this.message = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
