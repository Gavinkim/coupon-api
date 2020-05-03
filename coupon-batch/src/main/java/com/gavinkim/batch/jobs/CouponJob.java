package com.gavinkim.batch.jobs;

import com.gavinkim.batch.config.JobParametersTargetDateValidator;
import com.gavinkim.batch.config.JobRunIdIncrementer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CouponJob {
    private static final String JOB_COUPON = "JOB_COUPON";
    private static final String STEP_EXPIRED_UPDATE_COUPON = "STEP_EXPIRED_UPDATE_COUPON";
    private static final String STEP_NOTI_BEFORE_EXPIRE_COUPON = "STEP_NOTI_BEFORE_EXPIRE_COUPON";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name = JOB_COUPON)
    public Job couponJob(Step expiredUpdateCouponStep,
                         Step notiBeforeExpireCouponStep){
        return jobBuilderFactory.get(JOB_COUPON)
                .incrementer(new JobRunIdIncrementer())
                .start(notiBeforeExpireCouponStep)
                //.validator(new JobParametersTargetDateValidator())
                .next(expiredUpdateCouponStep)
                .build();
    }

    @Bean
    public Step expiredUpdateCouponStep(ExpiredCouponUpdateTasklet expiredCouponUpdateTasklet) {
        return stepBuilderFactory.get(STEP_EXPIRED_UPDATE_COUPON).tasklet(expiredCouponUpdateTasklet).build();
    }

    @Bean
    public Step notiBeforeExpireCouponStep(NotiBeforeExpireCouponTasklet notiBeforeExpireCouponTasklet) {
        return stepBuilderFactory.get(STEP_NOTI_BEFORE_EXPIRE_COUPON).tasklet(notiBeforeExpireCouponTasklet).build();
    }

}
