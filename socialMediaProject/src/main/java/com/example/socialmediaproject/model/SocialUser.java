package com.example.socialmediaproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "socialUser", cascade = CascadeType.ALL)
    private SocialProfile socialProfile;

    @OneToMany(mappedBy = "socialUser", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<SocialGroup> groups = new HashSet<>();

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    public void setSocialProfile(SocialProfile socialProfile) {
        this.socialProfile = socialProfile;
        if (socialProfile != null) {
            socialProfile.setSocialUser(this);
        }
    }
}
