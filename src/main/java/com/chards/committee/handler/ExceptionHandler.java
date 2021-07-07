//package com.chards.committee.handler;
//
//import com.chards.committee.config.BusinessException;
//import com.chards.committee.vo.Code;
//import com.chards.committee.vo.R;
//import com.fasterxml.jackson.core.JsonParseException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.support.DefaultMessageSourceResolvable;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.stream.Collectors;
//
//@Slf4j
//@ControllerAdvice
//public class ExceptionHandler {
//    /**
//     * 处理其他的异常
//     * @param e
//     * @return
//     */
//    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public R handle(Exception e){
////        如果抛出的自定义的BusinessException，则返回自定义的code和message
//        if (e instanceof BusinessException){
//            BusinessException businessException = (BusinessException) e;
//            return R.failure(businessException.getResposeCode());
//        } else {
//            log.error("[系统异常] {}", e);
//            return R.failure(Code.ERROR);
//        }
//    }
//
//    /**
//     * 处理空指针异常
//     * @param e
//     * @return
//     */
//    @org.springframework.web.bind.annotation.ExceptionHandler(value = NullPointerException.class)
//    @ResponseBody
//    public R nullPointerExceptionHandle(NullPointerException e){
//        log.error("[空指针异常 {}]", e);
//        return R.failure(Code.NULL_POINTER_ERROR);
//    }
//
//    /**
//     * 处理参数校验异常
//     * @param request
//     * @param e
//     * @return
//     */
//    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
//    @ResponseBody
//    public R methodArgumentNotValidExceptionHandle(HttpServletRequest request, MethodArgumentNotValidException e){
//        log.error("[参数校验异常 {}]", e);
//        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
//        return R.failure(message);
//    }
//
//    /**
//     * Json转换异常处理
//     * @param req
//     * @param e
//     * @return
//     */
//    @org.springframework.web.bind.annotation.ExceptionHandler(value = JsonParseException.class)
//    @ResponseBody
//    public R exceptionHandler(HttpServletRequest req, JsonParseException e) {
//        log.error("[JSON转换异常 {}]", e);
//        return R.failure(Code.PARAM_VALIDATION_ERROR);
//    }
//}
