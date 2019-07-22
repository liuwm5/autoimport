package com.liuweimin.adper.autoimport.exception;

import com.liuweimin.adper.autoimport.enums.ExceptionEnum;
import lombok.Data;

/*
 * @Author liuwm
 * @Description 定义异常
 * @Date 18:48 2019-07-07
 **/
@Data
public class BizException extends RuntimeException {
    private final ExceptionEnum code;

    public BizException(ExceptionEnum code, String msg) {
        super(msg);
        this.code = code;
    }
}
