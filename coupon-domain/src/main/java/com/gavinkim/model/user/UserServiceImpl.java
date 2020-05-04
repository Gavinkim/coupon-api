package com.gavinkim.model.user;

import com.gavinkim.dto.SignInRequestDto;
import com.gavinkim.dto.SignInResponseDto;
import com.gavinkim.dto.SignUpRequestDto;
import com.gavinkim.model.ValidationException;
import com.gavinkim.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Override
    public void checkUniqueEmail(String email) {
        if(userRepository.findByEmail(email.trim()) > 0){
            throw new ValidationException(String.format("[ %s ] 은 사용할 수 없습니다.",email));
        }
    }

    @Transactional
    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
        User user = userRepository.save(new User(signUpRequestDto));
        //todo: tempCode 와 이메일 발송
    }

    @Override
    public User signIn(SignInRequestDto signInRequestDto) {
        User user = findByUsername(signInRequestDto.getUsername())
                .orElseThrow(()->new SignInException("잘못된 아이디 혹은 패스워드 입니다."));
        if(Utils.nullSafeNotEquals(user.getPassword(),signInRequestDto.getPassword())){
            throw new SignInException("잘못된 아이디 혹은 패스워드 입니다.");
        }
        //todo: user 의 status 확인
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
