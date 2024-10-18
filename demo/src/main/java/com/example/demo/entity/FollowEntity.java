package com.example.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "follows")
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "following_user_id")
    UserEntity followingUser;

    @ManyToOne
    @JoinColumn(name = "followed_user_id")
    UserEntity followedUser;

    @Column(name = "created_at")
    Timestamp createdAt;
}
