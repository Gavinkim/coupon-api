package com.gavinkim.model.user;

import com.gavinkim.model.ValidationException;
import com.gavinkim.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException("요청하신 유저의 아이디는 존재 하지 않는 사용자 입니다."));
    }

    @Override
    public boolean checkUniqueUsername(String username) {
        if(Utils.isEmpty(username.trim())){
            throw new ValidationException("username 이 입력되지 않았습니다.");
        }
        return userRepository.countByUsername(username.trim()) <=0;
    }
}
