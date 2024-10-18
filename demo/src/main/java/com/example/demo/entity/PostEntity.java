package com.example.demo.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "title")
    String title;

    @Column(name = "body")
    byte[] body;

    @Column(name = "status")
    String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userPost;

    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL)
    Set<CommentEntity> comments;

    @OneToMany(mappedBy = "postVote", cascade = CascadeType.ALL)
    Set<VoteEntity> votes;

    @Column(name = "vote_count")
    int voteCount;

    @OneToMany(mappedBy = "postReport", cascade = CascadeType.ALL)
    List<ReportEntity> reports;

    @Column(name = "created_at")
    Timestamp createdAt;
}
