package com.gavinkim.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "[PageInfoDto] - 페이지 정보")
public class PageInfoDto {
    private int totalPages;
    private long totalElements;

}
