package com.example.socialmediaproject.repositories;

import com.example.socialmediaproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
