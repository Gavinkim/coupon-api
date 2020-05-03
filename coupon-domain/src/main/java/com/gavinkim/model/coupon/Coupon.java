package com.gavinkim.model.coupon;


import com.gavinkim.model.BaseEntity;
import com.gavinkim.type.CouponStatus;
import com.gavinkim.util.Utils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "coupon")
@Where(clause = "is_deleted='0'")
@SQLDelete(sql="UPDATE coupon SET is_deleted = '1' , updated_at=CURRENT_TIMESTAMP WHERE couponSeq = ?")
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long couponSeq;

    @Column(unique = true,length = 20,nullable = false)
    private String coupon;

    //@Column(updatable = false)
    @Setter
    private String expiredAt;

    @Setter
    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @Setter
    private boolean isDeleted;

    @Setter
    private Long userSeq;


    @Builder
    public Coupon(String coupon, String expiredAt,Long userSeq,CouponStatus status) {
        this.coupon = coupon;
        this.expiredAt = expiredAt;
        this.userSeq = userSeq;
        this.status = status;
    }

    public void assign(Long userSeq){
        String now = LocalDate.now().plusDays(2).format(Utils.YYYYMMDD);
        setUserSeq(userSeq);
        setStatus(CouponStatus.ASSIGNED);
        setExpiredAt(now);
    }
}
