package com.gavinkim.model.user;

import com.gavinkim.dto.SignUpRequestDto;
import com.gavinkim.model.BaseEntity;
import com.gavinkim.model.coupon.Coupon;
import com.gavinkim.type.UserStatus;
import com.gavinkim.util.Utils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.WAIT;

    @Column(nullable = false,unique = true)
    private String email;

    @Setter
    private String tempCode;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_seq"), inverseJoinColumns = @JoinColumn(name = "role_seq"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "userSeq")
    private List<Coupon> coupons = new ArrayList<>();

    @Builder
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
    }

    @Builder
    public User(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    //signup constructor
    public User(SignUpRequestDto signUpRequestDto, Set<Role> roles){
        this.username = signUpRequestDto.getUsername();
        this.password = signUpRequestDto.getPassword();
        this.email = signUpRequestDto.getEmail();
        this.tempCode = Utils.randomCode();
        this.roles = roles;
    }

}
