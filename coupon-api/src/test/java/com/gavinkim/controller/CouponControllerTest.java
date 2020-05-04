package com.gavinkim.controller;

import com.gavinkim.config.TokenAuthenticationEntryPoint;
import com.gavinkim.config.TokenAuthenticationFilter;
import com.gavinkim.controller.coupon.CouponController;
import com.gavinkim.dto.*;
import com.gavinkim.model.coupon.CouponService;
import com.gavinkim.security.CustomUserDetailsService;
import com.gavinkim.security.TokenProvider;
import com.gavinkim.type.CouponStatus;
import com.gavinkim.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("develop")
@RunWith(SpringRunner.class)
@WebMvcTest(CouponController.class)
public class CouponControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CouponService couponService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint;

    @MockBean
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @MockBean
    private TokenProvider tokenProvider;

    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0NEB0ZXN0LmNvbSIsImlhdCI6MTU4ODYyMjc4NiwiZXhwIjoxNTg4NjMzNTg2fQ.qLjKH3vbwpgjOSCEwoVMJbONezqtVBoOHOFRFxf_baOfJv-rRu8rNiqzQQkZ7rlXB-wkyxslGIsoqXFSmzkLBA";

    @Test
    public void create() throws Exception {
        long count = 100000;
        given(couponService.generateCoupon(count))
                .willReturn(count);
        mvc.perform(get("/coupons/create")
                //.header("Authorization", "Bearer " + token)
                .param("count", String.valueOf(count))
        ).andExpect(status().isCreated());

        verify(couponService).generateCoupon(count);
    }

    @Test
    public void assignCoupon() throws Exception{

        String mockCoupon = "test-coupon-124";
        given(couponService.assignCoupon(anyLong()))
                .willReturn(mockCoupon);
        mvc.perform(post("/coupons/assign/1")
                //.header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
        verify(couponService).assignCoupon(1);
    }
    @Test
    public void searchUserCoupon() throws Exception{
        SearchDto mockSearchDto = SearchDto.builder()
                .page(1)
                .size(1)
                .userSeq(1l)
                .build();
        CouponResultDto mockCouponResultDto = CouponResultDto.builder()
                .pageInfo(PageInfoDto.builder()
                        .totalElements(1)
                        .totalPages(1)
                        .build())
                .coupons(Arrays.asList(CouponDto.builder()
                        .expiredAt("20200502")
                        .coupon("test")
                        .status(CouponStatus.ASSIGNED)
                        .build()))
                .build();
        given(couponService.searchUserCoupon(mockSearchDto))
                .willReturn(mockCouponResultDto);

        mvc.perform(post("/coupons/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.toJson(mockSearchDto))
        ).andExpect(status().isOk());

        verify(couponService).searchUserCoupon(refEq(mockSearchDto));
    }

    @Test
    public void usingCoupon() throws Exception{
        CouponDto mockCouponDto = CouponDto.builder()
                .status(CouponStatus.USED)
                .coupon("test")
                .expiredAt("20200502")
                .build();
        CouponRequestDto mockCouponRequestDto = CouponRequestDto.builder()
                .coupon("test")
                .status(CouponStatus.USED)
                .userSeq(1l)
                .build();

        given(couponService.manageCoupon(mockCouponRequestDto))
                .willReturn(mockCouponDto);
        mvc.perform(post("/coupons/using")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.toJson(mockCouponRequestDto))
        ).andExpect(status().isOk());
        verify(couponService,times(1)).manageCoupon(refEq(mockCouponRequestDto));
    }

    @Test
    public void cancelCoupon() throws Exception {
        CouponDto mockCouponDto = CouponDto.builder()
                .status(CouponStatus.CANCELED)
                .coupon("test")
                .expiredAt("20200502")
                .build();
        CouponRequestDto mockCouponRequestDto = CouponRequestDto.builder()
                .coupon("test")
                .status(CouponStatus.CANCELED)
                .userSeq(1l)
                .build();

        given(couponService.manageCoupon(mockCouponRequestDto))
                .willReturn(mockCouponDto);
        mvc.perform(post("/coupons/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.toJson(mockCouponRequestDto))
        ).andExpect(status().isOk());
        verify(couponService,times(1)).manageCoupon(refEq(mockCouponRequestDto));
    }

    @Test
    public void expiredUserCoupon() throws Exception{
        SearchDto mockSearchDto = SearchDto.builder()
                .page(1)
                .size(1)
                .userSeq(1l)
                .build();
        CouponResultDto mockCouponResultDto = CouponResultDto.builder()
                .pageInfo(PageInfoDto.builder()
                        .totalElements(1)
                        .totalPages(1)
                        .build())
                .coupons(Arrays.asList(CouponDto.builder()
                        .expiredAt("20200502")
                        .coupon("test")
                        .status(CouponStatus.ASSIGNED)
                        .build()))
                .build();
        given(couponService.expiredUserCoupon(mockSearchDto))
                .willReturn(mockCouponResultDto);
        mvc.perform(post("/coupons/expired")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Utils.toJson(mockSearchDto))
        ).andExpect(status().isOk());
        verify(couponService,times(1)).expiredUserCoupon(refEq(mockSearchDto));
    }
}