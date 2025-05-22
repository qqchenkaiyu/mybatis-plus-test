package com.baomidou.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ResponseBody
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("[handleValidationExceptions]", ex);
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            sb.append(fieldName).append(":").append(errorMessage).append(";");
        });
        return ApiResponse.error(sb.toString());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public ApiResponse ExceptionHandler(Throwable ex) {
        log.error("[defaultExceptionHandler]", ex);
        // 返回 ERROR CommonResult
        return ApiResponse.error("系统异常" + ex.getMessage());
    }
}
