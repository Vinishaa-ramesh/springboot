package com.example.socialmediaproject.repositories;

import com.example.socialmediaproject.model.SocialProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialProfileRepository extends JpaRepository<SocialProfile, Long> {
}
