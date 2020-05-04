package com.gavinkim.model.user;

public interface UserService {
    User getUserById(Long id);
    boolean checkUniqueUsername(String username);
}
