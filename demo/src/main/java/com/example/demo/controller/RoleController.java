package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.ApiResultResponse;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResultResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResultResponse.<RoleResponse>builder()
                .build()
                .<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResultResponse<List<RoleResponse>> getAll() {
        return ApiResultResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResultResponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResultResponse.<Void>builder().build();
    }
}
