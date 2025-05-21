package com.baomidou.common;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // 注解作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时生效
@Documented
public @interface LogRequest {
    String value() default ""; // 可选描述信息
}
