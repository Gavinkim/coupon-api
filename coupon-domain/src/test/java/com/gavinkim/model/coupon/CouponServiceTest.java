package com.gavinkim.model.coupon;

import com.gavinkim.model.user.UserRepository;
import com.gavinkim.model.user.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;


public class CouponServiceTest {

    @InjectMocks
    private CouponServiceImpl couponService;
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateCoupon() {
        couponService.generateCoupon(2);
        verify(couponRepository).saveAll(anyList());
    }

}