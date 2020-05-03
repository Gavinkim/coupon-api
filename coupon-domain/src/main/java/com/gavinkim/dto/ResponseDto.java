package com.gavinkim.dto;

import com.gavinkim.type.ResponseType;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "[ResponseDto] - 응답 정보")
public class ResponseDto<T> {
    private String code;
    private String message;
    private T data;

    @Builder
    public ResponseDto(String code,String message,T data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }


    public static <T> ResponseDto<T> create(ResponseType code, T data){
        return ResponseDto.<T>builder()
                .code(code.getCode())
                .message(code.getMessage())
                .data(data)
                .build();
    }


    public static <T> ResponseDto<T> error(ResponseType code){
        return create(code, null);
    }

    public static <T> ResponseDto<T> success(T data){
        return create(ResponseType.SUCCESS,data);
    }

    public static <T> ResponseDto<T> success(){
        return create(ResponseType.SUCCESS, null);
    }
}
