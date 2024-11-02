package com.example.demo.model;

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
    User followingUser;

    @ManyToOne
    @JoinColumn(name = "followed_user_id")
    User followedUser;

    @Column(name = "created_at")
    Timestamp createdAt;
}
