package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "email")
    String email;

    @Column(name = "created_at")
    Long createdAt;

    @OneToMany(mappedBy = "userVote", cascade = CascadeType.ALL)
    List<VoteEntity> votesUser;

    @OneToMany(mappedBy = "userPost", cascade = CascadeType.ALL)
    List<PostEntity> postsUser;

    @OneToMany(mappedBy = "userComment", cascade = CascadeType.ALL)
    List<CommentEntity> commentsUser;

    @OneToMany(mappedBy = "followingUser", cascade = CascadeType.ALL)
    List<FollowEntity> followingUser;

    @OneToMany(mappedBy = "followedUser", cascade = CascadeType.ALL)
    List<FollowEntity> followedUser;

    @OneToMany(mappedBy = "userSetting", cascade = CascadeType.ALL)
    List<UserSettingEntity> settingsUser;

    @OneToMany(mappedBy = "userReport", cascade = CascadeType.ALL)
    List<ReportEntity> reportsUser;

    @OneToMany(mappedBy = "userNotification", cascade = CascadeType.ALL)
    List<NotificationEntity> notificationsUser;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    List<MessageEntity> messagesSent;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    List<MessageEntity> messagesReceived;

    @OneToMany(mappedBy = "userReply", cascade = CascadeType.ALL)
    List<ReplyEntity> repliesUser;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_code"))
    List<Role> roles;
}
