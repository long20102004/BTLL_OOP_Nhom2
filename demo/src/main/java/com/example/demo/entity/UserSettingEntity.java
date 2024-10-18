package com.example.demo.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_settings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity userSetting;

    @Column(name = "setting_key")
    String settingKey;

    @Column(name = "setting_value")
    String settingValue;

    @Column(name = "created_at")
    java.sql.Timestamp createdAt;

    @Column(name = "updated_at")
    java.sql.Timestamp updatedAt;

    @Column(name = "deleted_at")
    java.sql.Timestamp deletedAt;
}
