package com.example.socialmediaproject;

import com.example.socialmediaproject.model.Post;
import com.example.socialmediaproject.model.SocialGroup;
import com.example.socialmediaproject.model.SocialProfile;
import com.example.socialmediaproject.model.SocialUser;
import com.example.socialmediaproject.repositories.PostRepository;
import com.example.socialmediaproject.repositories.SocialGroupRepository;
import com.example.socialmediaproject.repositories.SocialProfileRepository;
import com.example.socialmediaproject.repositories.SocialUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
public class DataInitializer {
    private final SocialUserRepository socialUserRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final SocialGroupRepository socialGroupRepository;
    private final PostRepository postRepository;

    public DataInitializer(SocialUserRepository socialUserRepository, SocialProfileRepository socialProfileRepository, SocialGroupRepository socialGroupRepository, PostRepository postRepository) {
        this.socialUserRepository = socialUserRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.socialGroupRepository = socialGroupRepository;
        this.postRepository = postRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return (args -> {
            SocialUser user1 = new SocialUser();
            SocialUser user2 = new SocialUser();
            SocialUser user3 = new SocialUser();

            socialUserRepository.save(user1);
            socialUserRepository.save(user2);
            socialUserRepository.save(user3);

            SocialGroup socialGroup1 = new SocialGroup();
            SocialGroup socialGroup2 = new SocialGroup();

            socialGroup1.getSocialUsers().add(user1);
            socialGroup1.getSocialUsers().add(user2);

            socialGroup2.getSocialUsers().add(user2);
            socialGroup2.getSocialUsers().add(user3);

            socialGroupRepository.save(socialGroup1);
            socialGroupRepository.save(socialGroup2);

            user1.getGroups().add(socialGroup1);
            user2.getGroups().add(socialGroup1);
            user2.getGroups().add(socialGroup2);
            user3.getGroups().add(socialGroup2);

            socialUserRepository.save(user1);
            socialUserRepository.save(user2);
            socialUserRepository.save(user3);

            Post post1 = new Post();
            Post post2 = new Post();
            Post post3 = new Post();

            post1.setSocialUser(user1);
            post2.setSocialUser(user2);
            post3.setSocialUser(user3);

            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            SocialProfile socialProfile1 = new SocialProfile();
            SocialProfile socialProfile2 = new SocialProfile();
            SocialProfile socialProfile3 = new SocialProfile();

            socialProfile1.setSocialUser(user1);
            socialProfile2.setSocialUser(user2);
            socialProfile3.setSocialUser(user3);

            socialProfileRepository.save(socialProfile1);
            socialProfileRepository.save(socialProfile2);
            socialProfileRepository.save(socialProfile3);

            System.out.println("FETCHING SOCIAL USER");
            socialUserRepository.findById(1L);
        });
    }
}
