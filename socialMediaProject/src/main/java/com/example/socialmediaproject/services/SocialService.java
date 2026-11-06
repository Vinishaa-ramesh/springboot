package com.example.socialmediaproject.services;

import com.example.socialmediaproject.model.SocialProfile;
import com.example.socialmediaproject.model.SocialUser;
import com.example.socialmediaproject.repositories.SocialUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SocialService {
    private static final Logger logger = LoggerFactory.getLogger(SocialService.class);

    @Autowired
    SocialUserRepository socialUserRepository;

    public List<SocialUser> getAllUsers() {
        return socialUserRepository.findAll();
    }

    public SocialUser createUser(SocialUser socialUser) {
        return socialUserRepository.save(socialUser);
    }

    public SocialUser deleteUser(Long socialUserId) {
        SocialUser socialUser = socialUserRepository.findById(socialUserId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        try {
            socialUserRepository.delete(socialUser);
        } catch (RuntimeException exception) {
            logger.error("Failed to delete user with id {}", socialUserId, exception);
            throw exception;
        }
        return socialUser;
    }
}
