package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "votes")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User userVote;

    @ManyToOne
    @JoinColumn(name = "post_id")
    PostEntity postVote;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    CommentEntity commentVote;

    @Column(name = "vote_type")
    String voteType;

    @Column(name = "created_at")
    Timestamp createdAt;
}
