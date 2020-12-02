package com.chards.committee.config;

import com.chards.committee.vo.Code;
import lombok.Data;

/**
 * @author 远 chards_
 * @FileName:BusinessError
 * @date: 2020-07-22 12:23
 */
@Data
public class BusinessException extends RuntimeException {

    private Code resposeCode;

    public BusinessException(String message) {
        super(message);
    }


    public BusinessException(Code resposeCode) {
        this.resposeCode = resposeCode;
    }

    public static void error(String message) {
        throw new BusinessException(message);
    }


    public static void error(Code resposeCode) {
        throw new BusinessException(resposeCode);
    }

}
