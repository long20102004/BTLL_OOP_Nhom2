package com.example.demo.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUser(UserCreateRequest request);

    UserResponse toUserResponse(UserEntity user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget UserEntity user, UserUpdateRequest request);
}
