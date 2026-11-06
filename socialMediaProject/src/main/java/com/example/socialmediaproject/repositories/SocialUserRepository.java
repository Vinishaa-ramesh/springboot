package com.example.socialmediaproject.repositories;

import com.example.socialmediaproject.model.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
}
