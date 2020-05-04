package com.gavinkim.config;

import com.gavinkim.dto.ResponseDto;
import com.gavinkim.model.AlreadyException;
import com.gavinkim.model.ValidationException;
import com.gavinkim.model.coupon.CouponNotFoundException;
import com.gavinkim.model.coupon.NotAvailableCouponException;
import com.gavinkim.model.user.UserNotFoundException;
import com.gavinkim.type.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotAvailableCouponException.class)
    public ResponseEntity<?> notAvailableCouponException(NotAvailableCouponException e){
        log.error("[!] {}",e.getMessage());
        return new ResponseEntity<>(ResponseDto.error(ResponseType.NOT_AVAILABLE), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<?> couponNotFoundException(CouponNotFoundException e){
        log.error("[!] {}",e.getMessage());
        return new ResponseEntity<>(ResponseDto.error(ResponseType.COUPON_NOTFOUND), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException e){
        log.error("[!] {}",e.getMessage());
        return new ResponseEntity<>(ResponseDto.error(ResponseType.USER_NOTFOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyException.class)
    public ResponseEntity<?> alreadyFoundException(AlreadyException e){
        log.error("[!] {}",e.getMessage());
        return new ResponseEntity<>(ResponseDto.error(ResponseType.ALREADY), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationException(ValidationException e){
        log.error("[!] {}",e.getMessage());
        ResponseType.VALIDATION.setMessage(e.getMessage());
        return new ResponseEntity<>(ResponseDto.error(ResponseType.VALIDATION), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpectedException(Exception e, WebRequest request) {
        log.error("[!] {}",e.getMessage());
        return new ResponseEntity<>(ResponseDto.error(ResponseType.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
