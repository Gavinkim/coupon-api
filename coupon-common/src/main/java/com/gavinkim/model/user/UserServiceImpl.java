package com.gavinkim.model.user;

import com.gavinkim.dto.SignUpRequestDto;
import com.gavinkim.model.SystemException;
import com.gavinkim.model.ValidationException;
import com.gavinkim.type.RoleName;
import com.gavinkim.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final int USERNAME_LENGTH_MIN = 4;
    private final int USERNAME_LENGTH_MAX = 30;

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository
                            ,RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
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
        if(userRepository.countByEmail(email.trim()) > 0){
            throw new ValidationException(String.format("[ %s ] 은 사용할 수 없습니다.",email));
        }
    }

    @Transactional
    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
        Role userRole = roleService.getRoleByType(RoleName.ROLE_USER).orElseThrow(() -> new SystemException("User Role not set. Add default roles to database."));
        User user = userRepository.save(new User(signUpRequestDto, Collections.singleton(userRole)));
        //todo: tempCode 와 이메일 발송
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("사용자를 찾을수 없습니다."));
        return Optional.of(user);
    }
}
