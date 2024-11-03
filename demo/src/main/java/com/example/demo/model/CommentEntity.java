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
    int id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    PostEntity postComment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User userComment;

    @OneToMany(mappedBy = "commentReply", cascade = CascadeType.ALL)
    List<ReplyEntity> replyComment;

    @OneToMany(mappedBy = "commentVote", cascade = CascadeType.ALL)
    List<VoteEntity> votes;

    @OneToMany(mappedBy = "commentReport", cascade = CascadeType.ALL)
    List<ReportEntity> reports;

    @Column(name = "body")
    byte[] body;

    @Column(name = "created_at")
    Timestamp createdAt;
}
