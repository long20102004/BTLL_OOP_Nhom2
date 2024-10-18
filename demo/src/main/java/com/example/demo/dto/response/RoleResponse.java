package com.example.demo.dto.response;

import java.util.List;

import com.example.demo.entity.Permission;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String code;
    String description;
    List<Permission> permissions;
}
