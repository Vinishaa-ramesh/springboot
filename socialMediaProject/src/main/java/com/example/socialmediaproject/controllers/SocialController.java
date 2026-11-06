package com.example.socialmediaproject.controllers;

import com.example.socialmediaproject.model.SocialUser;
import com.example.socialmediaproject.services.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SocialController {
    @Autowired
    private SocialService socialService;

    @GetMapping("/social/users")
    public ResponseEntity<List<SocialUser>> getAllUsers() {
        return new ResponseEntity<>(socialService.getAllUsers(),HttpStatus.OK);
    }
    @PostMapping("/social/users")
    public ResponseEntity<SocialUser> createUser(@RequestBody SocialUser socialUser) {
        return new ResponseEntity<>(socialService.createUser(socialUser), HttpStatus.OK);
    }
    @DeleteMapping("/social/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long socialUserId) {
        socialService.deleteUser(socialUserId);
        return new ResponseEntity<>("Deleted user with Id: "+socialUserId, HttpStatus.OK);
    }
}
