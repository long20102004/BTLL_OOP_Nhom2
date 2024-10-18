package com.example.demo.entity;

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
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity userNotification;

    @Column(name = "message")
    String message;

    @Column(name = "read_status")
    boolean readStatus;

    @Column(name = "created_at")
    Timestamp createdAt;
}
