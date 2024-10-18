package com.example.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "messages")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    UserEntity receiver;

    @Column(name = "content")
    byte[] content;

    @Column(name = "time_send")
    Timestamp timeSend;
}
