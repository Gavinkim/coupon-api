package com.gavinkim.controller.user;

import com.gavinkim.dto.*;
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
    private final SignInResource signInResource;

    @Autowired
    public UserController(UserService userService,
                          SignInResource signInResource) {
        this.userService = userService;
        this.signInResource = signInResource;
    }

    @ApiOperation(value = "signup", nickname = "signup",notes = "회원가입")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = CouponDto.class)})
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        userService.signUp(signUpRequestDto);
        return new ResponseEntity<>(ResponseDto.success(),HttpStatus.CREATED);
    }

    @ApiOperation(value = "signin", nickname = "signin",notes = "로그인")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = SignInResponseDto.class)})
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignInRequestDto signInRequestDto){
        return new ResponseEntity<>(ResponseDto.success(signInResource.signin(signInRequestDto)),HttpStatus.OK);
    }

    @ApiOperation(value = "validation_username", nickname = "validation_username",notes = "validation_username")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = ResponseDto.class)})
    @GetMapping("/username/validation")
    public ResponseEntity<?> checkUsername(@RequestParam(name = "username") String username){
        userService.checkUniqueUsername(username);
        return new ResponseEntity<>(ResponseDto.success(),HttpStatus.OK);
    }

    @ApiOperation(value = "validation_email", nickname = "validation_email",notes = "validation_email")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = ResponseDto.class)})
    @GetMapping("/email/validation")
    public ResponseEntity<?> checkEmail(@RequestParam(name = "email") String email){
        userService.checkUniqueEmail(email);
        return new ResponseEntity<>(ResponseDto.success(),HttpStatus.OK);
    }

    @ApiOperation(value = "confirm_email", nickname = "confirm_email",notes = "confirm_email")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = ResponseDto.class)})
    @GetMapping("/email/confirm")
    public ResponseEntity<?> confirmEmail(@RequestParam(name = "email") String email,@RequestParam(name = "code") String code){
        userService.checkUniqueEmail(email);
        return new ResponseEntity<>(ResponseDto.success(),HttpStatus.OK);
    }

}
