package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userReport;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private PostEntity postReport;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private CommentEntity commentReport;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_at")
    private Timestamp createdAt;

    // Getters and setters
}
