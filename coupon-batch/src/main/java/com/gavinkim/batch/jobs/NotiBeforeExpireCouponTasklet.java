package com.gavinkim.batch.jobs;

import com.gavinkim.model.coupon.Coupon;
import com.gavinkim.model.coupon.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@StepScope
public class NotiBeforeExpireCouponTasklet implements Tasklet {
    private final static int AFTER_DAY = 3;
    private final CouponService couponService;

    @Autowired
    public NotiBeforeExpireCouponTasklet(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LocalDate now = LocalDate.now();
        Optional<List<Coupon>> optCoupons = couponService.getBeforeExpireCoupon(now,AFTER_DAY);
        if(optCoupons.isPresent()){
            optCoupons.get().stream().forEach(coupon -> {
                log.info(String.format(">>> 쿠폰 [%s] 3일 후 만료 됩니다.",coupon.getCoupon()));
            });
            return RepeatStatus.FINISHED;
        }else{
            log.info("[!] >>>> 만료 예정인 쿠폰이 존재 하지 않습니다.");
            return RepeatStatus.FINISHED;
        }
    }
}
