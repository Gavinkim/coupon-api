package com.gavinkim.controller;

import com.gavinkim.dto.*;
import com.gavinkim.model.coupon.CouponService;
import com.gavinkim.type.CouponStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Api(tags = {"쿠폰"},description = "쿠폰 서비스")
@RestController
@RequestMapping(path="/coupons", produces = MediaType.APPLICATION_JSON_VALUE)
public class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    //쿠폰 생성
    @ApiOperation(value = "create", nickname = "create",notes = "입력된 건수 만큼 쿠폰을 생성 합니다.")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = ResponseDto.class)})
    @GetMapping("/create")
    public ResponseEntity<?> createCoupon(@RequestParam("count") Long count){
        long createdCount = couponService.generateCoupon(count);
        return new ResponseEntity<>(ResponseDto.success(createdCount), HttpStatus.CREATED);
    }

    //쿠폰 지급
    @ApiOperation(value = "assign", nickname = "assign",notes = "요청한 사용자(id)에게 쿠폰이 지급 됩니다.")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = ResponseDto.class)})
    @PostMapping("assign/{id}")
    public ResponseEntity<?> assignCoupon(@PathVariable("id") Long id){
        String coupon = couponService.assignCoupon(id);
        return new ResponseEntity<>(ResponseDto.success(coupon), HttpStatus.OK);
    }

    //지급된 쿠폰 조회
    @ApiOperation(value = "search", nickname = "search",notes = "사용자에게 지급된 쿠폰 리스트를 보여줍니다.")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = CouponResultDto.class)})
    @PostMapping("/search")
    public ResponseEntity<?> searchUserCoupon(@Valid @RequestBody SearchDto searchDto){
        return new ResponseEntity<>(ResponseDto.success(couponService.searchUserCoupon(searchDto)), HttpStatus.OK);
    }

    //쿠폰 사용 요청
    @ApiOperation(value = "using", nickname = "using",notes = "요청한 쿠폰을 사용하도록 합니다.")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = CouponDto.class)})
    @PostMapping("/using")
    public ResponseEntity<?> usingCoupon(@Valid @RequestBody CouponRequestDto couponRequestDto){
        couponRequestDto.setStatus(CouponStatus.USED);
        return new ResponseEntity<>(ResponseDto.success(couponService.manageCoupon(couponRequestDto)), HttpStatus.OK);
    }

    //쿠폰 취소 요청
    @ApiOperation(value = "cancel", nickname = "cancel",notes = "요청한 쿠폰을 취소 합니다.")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = CouponDto.class)})
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelCoupon(@Valid @RequestBody CouponRequestDto couponRequestDto){
        couponRequestDto.setStatus(CouponStatus.CANCELED);
        return new ResponseEntity<>(ResponseDto.success(couponService.manageCoupon(couponRequestDto)), HttpStatus.OK);
    }

    //당일 만료된 쿠폰 리스트
    @ApiOperation(value = "expired", nickname = "expired",notes = "당일 만료된 쿠폰 리스트를 보여줍니다.")
    @ApiResponses({@ApiResponse(code=200,message = "success",response = CouponResultDto.class)})
    @PostMapping("/expired")
    public ResponseEntity<?> expiredUserCoupon(@Valid @RequestBody SearchDto searchDto){
        return new ResponseEntity<>(ResponseDto.success(couponService.expiredUserCoupon(searchDto)), HttpStatus.OK);
    }

}
