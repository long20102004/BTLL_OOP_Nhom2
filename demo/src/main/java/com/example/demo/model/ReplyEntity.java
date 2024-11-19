package com.example.demo.model;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Entity
@Table(name = "replies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity commentReply;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userReply;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
