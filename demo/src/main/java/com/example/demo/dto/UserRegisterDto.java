package com.example.demo.dto;

import com.example.demo.custom_annotation.PasswordMatches;
import com.example.demo.custom_annotation.UserExisted;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches(message = "Confirm password not match with password!")
@UserExisted(message = "This email has been used, use another email!")
public class UserRegisterDto {
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "Name is required")
    @Size(min = 4, max = 10, message = "Name length is between 4 and 10")
    private String displayName;

    @NotEmpty(message = "Confirm Password is required")
    private String confirmPassword;
}