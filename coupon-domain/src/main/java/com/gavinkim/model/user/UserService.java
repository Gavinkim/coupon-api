package com.gavinkim.model.user;

import com.gavinkim.dto.SignInRequestDto;
import com.gavinkim.dto.SignUpRequestDto;

import java.util.Optional;

public interface UserService {
    User getUserById(Long id);
    Optional<User> findByUsername(String username);
    void checkUniqueUsername(String username);
    void checkUniqueEmail(String email);
    void signUp(SignUpRequestDto signUpRequestDto);
    User signIn(SignInRequestDto signInRequestDto);

}
