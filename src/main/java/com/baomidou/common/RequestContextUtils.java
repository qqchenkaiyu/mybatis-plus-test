package com.baomidou.common;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RequestContextUtils {

    private static final String API_OPERATION_KEY = "CURRENT_API_OPERATION";

    /**
     * 存入当前接口方法的描述信息（@ApiOperation.value()）
     */
    public static void setApiOperation(String value) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.setAttribute(API_OPERATION_KEY, value, RequestAttributes.SCOPE_REQUEST);
        }
    }

    /**
     * 获取当前接口方法的描述信息
     */
    public static String getApiOperation() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            Object value = requestAttributes.getAttribute(API_OPERATION_KEY, RequestAttributes.SCOPE_REQUEST);
            return value != null ? value.toString() : null;
        }
        return "";
    }

    /**
     * 清除当前请求中的值（可选，通常由 Spring 自动管理生命周期）
     */
    public static void clearApiOperation() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.removeAttribute(API_OPERATION_KEY, RequestAttributes.SCOPE_REQUEST);
        }
    }
}
