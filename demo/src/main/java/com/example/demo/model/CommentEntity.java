package com.example.demo.model;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postComment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userComment;

    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "content")
    private String content;
    @Column(name = "upvote")
    private int upvote;
    @Column(name = "downvote")
    private int downVote;
}
