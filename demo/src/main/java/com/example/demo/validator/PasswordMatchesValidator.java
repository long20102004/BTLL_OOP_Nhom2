package com.example.demo.validator;

import com.example.demo.custom_annotation.PasswordMatches;
import com.example.demo.dto.UserRegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegisterDto> {
    @Override
    public boolean isValid(UserRegisterDto userRegisterDto, ConstraintValidatorContext context) {
        return userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword());
    }
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
}