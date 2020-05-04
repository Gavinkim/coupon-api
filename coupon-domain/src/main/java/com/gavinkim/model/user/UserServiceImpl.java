package com.gavinkim.model.user;

import com.gavinkim.model.ValidationException;
import com.gavinkim.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final int USERNAME_LENGTH_MIN = 4;
    private final int USERNAME_LENGTH_MAX = 30;
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
    public void checkUniqueUsername(String username) {

        if(Utils.isEmpty(username.trim())){
            throw new ValidationException("username 을 입력해주세요");
        }else if(!Utils.checkBetween(username,USERNAME_LENGTH_MIN,USERNAME_LENGTH_MAX)){
            throw new ValidationException("username 은 4 ~ 30자 사이여야 합니다.");
        }else if(userRepository.countByUsername(username.trim()) > 0){
            throw new ValidationException(String.format("[ %s ] 은 사용할 수 없습니다.",username));
        }
    }
}
