package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.response.ApiResultResponse;
import com.example.demo.dto.response.PermissionResponse;
import com.example.demo.service.PermissionService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResultResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResultResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResultResponse<List<PermissionResponse>> getAll() {
        return ApiResultResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResultResponse<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiResultResponse.<Void>builder().build();
    }
}
