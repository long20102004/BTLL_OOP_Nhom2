package com.example.demo.model;

import java.sql.Date;
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
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userPost;

    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
    @Column(name = "upvote")
    private int upvote;
    @Column(name = "down_vote")
    private int downVote;


    @OneToMany(mappedBy = "postReport", cascade = CascadeType.ALL)
    private List<ReportEntity> reports;

    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "image")
    private String imageUrl;
    @Column(name = "upvoted")
    private boolean upvoted;
    @Column(name = "downvoted")
    private boolean downvoted;
    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    List<TagEntity> postsTag;
}
