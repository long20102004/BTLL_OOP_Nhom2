package com.example.demo.model;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.example.demo.dto.UserRegisterDto;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "email")
    private String email;
    @Column(name = "display_name")
    private String displayName;

    @Column(name = "created_at")
    private Date createdAt;
    @OneToMany(mappedBy = "userPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<PostEntity> postsUser;
    @ManyToMany
    @JoinTable(name = "users_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagEntity> tagEntities;

    @OneToMany(mappedBy = "userComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CommentEntity> commentsUser;
    @Column(name = "avatar")
    private String avatar = "/imgs/avatar1.png";
    @Column(name = "description")
    private String description;
    @Column(name = "rating")
    private Integer rating = 0;

    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserFollow> following;

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserFollow> followers;

    public User(UserRegisterDto userRegisterDto){
        this.displayName = userRegisterDto.getDisplayName();
        this.email = userRegisterDto.getEmail();
        this.username = userRegisterDto.getEmail();
        this.password = userRegisterDto.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role));
    }


}
