package com.example.demo.model;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "user_follow")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    @Column(name = "created_at")
    private Date createdAt;
}
