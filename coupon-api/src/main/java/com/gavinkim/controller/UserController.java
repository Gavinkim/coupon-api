package com.gavinkim.controller;

import com.gavinkim.dto.CouponDto;
import com.gavinkim.dto.ResponseDto;
import com.gavinkim.dto.SignInResponseDto;
import com.gavinkim.dto.SignUpRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"사용자"},description = "사용자 서비스")
@RestController
@RequestMapping(path="/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @ApiOperation(value = "signup", nickname = "signup",notes = "회원가입")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = CouponDto.class)})
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        return null;
    }

    @ApiOperation(value = "signin", nickname = "signin",notes = "로그인")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = SignInResponseDto.class)})
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        return null;
    }
}
