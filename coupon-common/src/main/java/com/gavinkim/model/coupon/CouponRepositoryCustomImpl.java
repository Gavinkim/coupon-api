package com.gavinkim.model.coupon;

import com.gavinkim.dto.CouponDto;
import com.gavinkim.dto.CouponRequestDto;
import com.gavinkim.dto.SearchDto;
import com.gavinkim.type.CouponStatus;
import com.gavinkim.util.Utils;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class CouponRepositoryCustomImpl extends QuerydslRepositorySupport implements  CouponRepositoryCustom{
    private final QCoupon qCoupon = QCoupon.coupon1;
    public CouponRepositoryCustomImpl() {
        super(Coupon.class);
    }

    @Override
    public Optional<Coupon> getUsingCoupon(CouponRequestDto couponRequestDto) {
        String now = LocalDate.now().format(Utils.YYYYMMDD);
        return Optional.ofNullable(from(qCoupon)
                .where(qCoupon.isDeleted.isFalse()
                        .and(qCoupon.coupon.eq(couponRequestDto.getCoupon()))
                        .and(qCoupon.userSeq.eq(couponRequestDto.getUserSeq()))
                        .and(qCoupon.status.ne(CouponStatus.EXPIRED))
                        .and(qCoupon.status.isNotNull())
                        .and(qCoupon.expiredAt.gt(now)))
                .fetchFirst());
    }

    @Override
    public Optional<Coupon> getCancelCoupon(CouponRequestDto couponRequestDto) {
        String now = LocalDate.now().format(Utils.YYYYMMDD);
        return Optional.ofNullable(from(qCoupon)
                .where(qCoupon.isDeleted.isFalse()
                        .and(qCoupon.coupon.eq(couponRequestDto.getCoupon()))
                        .and(qCoupon.userSeq.eq(couponRequestDto.getUserSeq()))
                        .and(qCoupon.status.ne(CouponStatus.EXPIRED))
                        .and(qCoupon.status.ne(CouponStatus.USED))
                        .and(qCoupon.status.isNotNull())
                        .and(qCoupon.expiredAt.gt(now)))
                .fetchFirst());
    }

    @Override
    public Optional<Coupon> findOneWithNotAssigned() {
        String now = LocalDate.now().format(Utils.YYYYMMDD);
        return Optional.ofNullable(from(qCoupon)
                .where(qCoupon.status.isNull()
                        .and(qCoupon.isDeleted.isFalse())
                        .and(qCoupon.userSeq.isNull())
                        .and(qCoupon.status.isNull()))
                .fetchFirst()
        );
    }

    @Override
    public Page<CouponDto> getUserCoupon(SearchDto searchDto) {
        QueryResults<CouponDto> result = from(qCoupon)
                .select(Projections.bean(CouponDto.class,
                        qCoupon.coupon.as("coupon"),
                        qCoupon.expiredAt.as("expiredAt"),
                        qCoupon.status.as("status")))
                .where(qCoupon.userSeq.eq(searchDto.getUserSeq())
                        .and(qCoupon.isDeleted.isFalse()))
                .limit(searchDto.getSize())
                .offset(searchDto.getPage()-1)
                .fetchResults();
        return new PageImpl<>(result.getResults(),searchDto.getPageable(), result.getTotal());
    }

    @Override
    public Page<CouponDto> getExpiredUserCoupon(SearchDto searchDto) {
        String now = LocalDate.now().format(Utils.YYYYMMDD);
        QueryResults<CouponDto> result = from(qCoupon)
                .select(Projections.bean(CouponDto.class,
                        qCoupon.coupon.as("coupon"),
                        qCoupon.expiredAt.as("expiredAt"),
                        qCoupon.status.as("status")))
                .where(qCoupon.userSeq.eq(searchDto.getUserSeq())
                .and(qCoupon.expiredAt.eq(now))
                .or(qCoupon.status.eq(CouponStatus.EXPIRED)))
                .limit(searchDto.getSize())
                .offset(searchDto.getPage()-1)
                .fetchResults();
        return new PageImpl<>(result.getResults(),searchDto.getPageable(), result.getTotal());
    }

    @Override
    public List<Coupon> getBeforeExpireCoupon(LocalDate today,int after) {
        String now = today.format(Utils.YYYYMMDD);
        String nowAfter = today.plusDays(after).format(Utils.YYYYMMDD);
        return from(qCoupon).where(
                qCoupon.expiredAt.loe(nowAfter)
                .and(qCoupon.expiredAt.gt(now))
                .and(qCoupon.status.ne(CouponStatus.USED))
                .and(qCoupon.status.isNotNull())
        ).fetch();
    }

    @Override
    public List<Coupon> getExpiredCoupon(LocalDate today) {
        String now = today.format(Utils.YYYYMMDD);
        return from(qCoupon).where(
                qCoupon.expiredAt.loe(now)
                .and(qCoupon.status.ne(CouponStatus.EXPIRED))
                .and(qCoupon.status.ne(CouponStatus.USED))
                .and(qCoupon.userSeq.isNotNull())
        ).fetch();
    }
}
