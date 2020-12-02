package com.chards.committee.vo;

import lombok.Data;

//统一返回结果
@Data
public class R<T> {
    private Integer code;
    private String message;
    private T data;

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> R<T> success(T data) {
        return getInstance(Code.SUCCESS, data);
    }

    public static <T> R<T> failure(String message) {
        return failure(-1, message);
    }

    public static <T> R<T> failure(Code code) {
        return failure(code.code, code.message);
    }

    public static <T> R<T> failure(Integer code, String message) {
        return getInstance(code, message, null);
    }

    private static <T> R<T> getInstance(Code code, T data) {
        return getInstance(code.code, code.message, data);
    }

    public static <T> R<T> getInstance(Integer code, String message, T data) {
        if (data instanceof Boolean && data.equals(false)) {
            code = Code.FAILURE.code;
            message = Code.FAILURE.message;
        }
        return new R<>(code, message, data);
    }

}
