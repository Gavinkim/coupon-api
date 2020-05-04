package com.gavinkim.controller;

import com.gavinkim.dto.CouponDto;
import com.gavinkim.dto.ResponseDto;
import com.gavinkim.dto.SignInResponseDto;
import com.gavinkim.dto.SignUpRequestDto;
import com.gavinkim.model.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = {"사용자"},description = "사용자 서비스")
@RestController
@RequestMapping(path="/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "signup", nickname = "signup",notes = "회원가입")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = CouponDto.class)})
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto){

        return new ResponseEntity<>(ResponseDto.success(),HttpStatus.CREATED);
    }

    @ApiOperation(value = "signin", nickname = "signin",notes = "로그인")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = SignInResponseDto.class)})
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        return new ResponseEntity<>(ResponseDto.success(),HttpStatus.OK);
    }

//    @ApiOperation(value = "validation_username", nickname = "validation_username",notes = "validation_username")
//    @ApiResponses({@ApiResponse(code=200,message = "success",response = SignInResponseDto.class)})
//    @GetMapping("/validation/{username:[a-zA-Z0-9]{4,30}}")
//    public ResponseEntity<?> validationUsername(@PathVariable("username") String username) {
//        log.info("USER: {}",username);
//        return new ResponseEntity<>(ResponseDto.success(),HttpStatus.OK);
//    }

    @ApiOperation(value = "validation_username", nickname = "validation_username",notes = "validation_username")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = ResponseDto.class)})
    @GetMapping("/validation")
    public ResponseEntity<?> checkUsername(@RequestParam(name = "username") String username){
        userService.checkUniqueUsername(username);
        return new ResponseEntity<>(ResponseDto.success(),HttpStatus.OK);
    }

}
