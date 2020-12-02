package com.chards.committee.config;

import com.chards.committee.vo.Code;
import com.chards.committee.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler(IllegalArgumentException.class)
    public R illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return R.failure(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return R.failure(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R accessDeniedExceptionHandler() {
        return R.failure(Code.PERMISSION_NO_ACCESS);
    }

    @ExceptionHandler(value = BusinessException.class)
    public R businessExceptionHandler(BusinessException businessException) {
        return businessException.getMessage() == null ? R.failure(businessException.getResposeCode()) : R.failure(businessException.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public R missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException missingServletRequestParameterException) {
        return R.failure(Code.PARAM_IS_BLANK);
    }

    @ExceptionHandler(value = Exception.class)
    public R exceptionHandler(Exception e) {
        log.error(" error : "+e.getMessage(),e);
        return R.failure(Code.ERROR);
    }
}
