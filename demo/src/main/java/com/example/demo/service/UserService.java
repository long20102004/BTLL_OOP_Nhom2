package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.converter.RoleMapper;
import com.example.demo.converter.UserMapper;
import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.UserEntity;
import com.example.demo.exeption.AppException;
import com.example.demo.exeption.ErrorCode;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

        UserEntity user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<Role> roles = roleRepository.findAllByCode("USER");
        user.setRoles(roles);
        UserResponse userResponse = userMapper.toUserResponse(userRepository.save(user));
        userResponse.setRoles(roles.stream().map(roleMapper::toRoleResponse).toList());
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserEntity user =
                userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setRoles(
                user.getRoles().stream().map(roleMapper::toRoleResponse).toList());
        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        UserEntity user =
                userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roleList = roleRepository.findAllById(request.getRoles());
        user.setRoles(new ArrayList<>(roleList));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        } else {
            List<UserResponse> userResponses = new ArrayList<>();
            for (UserEntity user : users) {
                UserResponse userResponse = userMapper.toUserResponse(user);
                userResponse.setRoles(
                        user.getRoles().stream().map(roleMapper::toRoleResponse).toList());
                userResponses.add(userResponse);
            }
            return userResponses;
        }
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
        log.info("In method get user by Id");
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
}
