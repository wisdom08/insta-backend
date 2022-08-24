package com.insta.exceptionss;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionResponseDto {
    private String code;
    private String msg;




    public ExceptionResponseDto(Code code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

}