package com.example.demo.validator;

import com.example.demo.custom_annotation.UserExisted;
import com.example.demo.dto.UserRegisterDto;
import com.example.demo.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserExistedValidator implements ConstraintValidator<UserExisted, UserRegisterDto> {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void initialize(UserExisted constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserRegisterDto userRegisterDto, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByUsername(userRegisterDto.getEmail()) == null;
    }

}
