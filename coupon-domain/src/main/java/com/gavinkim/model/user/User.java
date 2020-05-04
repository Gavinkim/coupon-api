package com.gavinkim.model.user;

import com.gavinkim.dto.SignUpRequestDto;
import com.gavinkim.model.BaseEntity;
import com.gavinkim.model.coupon.Coupon;
import com.gavinkim.type.UserStatus;
import com.gavinkim.util.Utils;
import lombok.*;
import org.springframework.stereotype.Component;

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

    @Setter
    @Column(name = "status",nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.WAIT;

    @Column(nullable = false,unique = true)
    private String email;

    @Setter
    private String tempCode;

    @OneToMany(mappedBy = "userSeq")
    private List<Coupon> coupons = new ArrayList<>();

    @Builder
    public User(String username, String password, String email,List<Coupon> coupons) {
        this.username = username;
        this.password = password;
        this.coupons = coupons;
    }

    //signup constructor
    public User(SignUpRequestDto signUpRequestDto){
        this.username = signUpRequestDto.getUsername();
        this.password = signUpRequestDto.getPassword();
        this.email = signUpRequestDto.getEmail();
        this.tempCode = Utils.randomCode();
    }

}
