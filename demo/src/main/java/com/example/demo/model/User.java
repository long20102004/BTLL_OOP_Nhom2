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
