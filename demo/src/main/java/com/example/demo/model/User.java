package com.example.demo.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    String username;

    @Column(name = "password_hash")
    String password;

    @Column(name = "email")
    String email;
    @Column(name = "display_name")
    String displayName;

//    @Column(name = "created_at")
//    Long createdAt;

//    @OneToMany(mappedBy = "userVote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<VoteEntity> votesUser;
//
    @OneToMany(mappedBy = "userPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<PostEntity> postsUser;
//
//    @OneToMany(mappedBy = "userComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<CommentEntity> commentsUser;
//
//    @OneToMany(mappedBy = "followingUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<FollowEntity> followingUser;
//
//    @OneToMany(mappedBy = "followedUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<FollowEntity> followedUser;
//
//    @OneToMany(mappedBy = "userSetting", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<UserSettingEntity> settingsUser;
//
//    @OneToMany(mappedBy = "userReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<ReportEntity> reportsUser;
//
//    @OneToMany(mappedBy = "userNotification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<NotificationEntity> notificationsUser;
//
//    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<MessageEntity> messagesSent;
//
//    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<MessageEntity> messagesReceived;
//
//    @OneToMany(mappedBy = "userReply", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<ReplyEntity> repliesUser;

    @Column(name = "role")

    private String role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
