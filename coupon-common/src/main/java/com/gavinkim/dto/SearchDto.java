package com.gavinkim.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@ApiModel(value = "[SearchDto] - 검색 정보")
@NoArgsConstructor
public class SearchDto {
    private final static int MAX = 10;
    private Long userSeq;
    private String coupon;
    //private CouponStatus status;
    private int size = MAX;
    private int page = 1;

    @Builder
    public SearchDto(Long userSeq, String coupon
            //, CouponStatus status
            , int size, int page) {
        this.userSeq = userSeq;
        this.coupon = coupon;
        //this.status = status;
        this.size = size;
        this.page = page;
    }

    @JsonIgnore
    public Pageable getPageable(){
        return PageRequest.of(getPage()-1,getSize());
    }
}
