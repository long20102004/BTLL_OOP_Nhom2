package com.example.demo.model;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "replies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    CommentEntity commentReply;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User userReply;

    @Column(name = "content")
    byte[] content;

    @Column(name = "created_at")
    java.sql.Timestamp createdAt;
}
