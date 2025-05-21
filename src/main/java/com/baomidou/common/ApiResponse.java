package com.baomidou.common;

import cn.hutool.core.thread.ThreadUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Pagination pagination;

    // 成功响应的静态工厂方法
    public static <T> ApiResponse<T> success(T data, Pagination pagination) {
        return new ApiResponse<>(true, "success", data, pagination);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, "success" , null, null);
    }

    public static <T> ApiResponse<T> data(boolean result) {
        // 从当前请求上下文中获取数据
        return new ApiResponse<>(result, RequestContextUtils.getApiOperation() + (result? "成功" : "失败") , null, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, RequestContextUtils.getApiOperation() + "成功" , data, null);
    }

    public static ApiResponse<String> error(String message) {
        return new ApiResponse<>(false, message , null, null);
    }

    // Getters 和 Setters
}
