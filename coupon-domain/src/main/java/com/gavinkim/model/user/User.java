package com.gavinkim.model.user;

import com.gavinkim.model.BaseEntity;
import com.gavinkim.model.coupon.Coupon;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(nullable = false,unique = true,length = 30)
    private String username;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    private boolean isDeleted;

    @OneToMany(mappedBy = "userSeq")
    private List<Coupon> coupons = new ArrayList<>();

    @Builder
    public User(String username, String password, List<Coupon> coupons) {
        this.username = username;
        this.password = password;
        this.coupons = coupons;
    }

}
