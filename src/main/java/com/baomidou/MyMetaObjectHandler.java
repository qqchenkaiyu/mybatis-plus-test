package com.baomidou;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        fillFields(metaObject, FieldFill.INSERT, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fillFields(metaObject, FieldFill.UPDATE, LocalDateTime.now());
    }

    /**
     * 根据 FieldFill 类型自动填充字段
     */
    private void fillFields(MetaObject metaObject, FieldFill fillType, LocalDateTime value) {
        // 获取当前实体类的 Class
        Class<?> originalClass = metaObject.getOriginalObject().getClass();

        // 遍历所有字段
        for (java.lang.reflect.Field field : originalClass.getDeclaredFields()) {
            // 查找带有 @TableField(fill = xxx) 的字段
            if (field.isAnnotationPresent(com.baomidou.mybatisplus.annotation.TableField.class)) {
                com.baomidou.mybatisplus.annotation.TableField tableField =
                        field.getAnnotation(com.baomidou.mybatisplus.annotation.TableField.class);

                // 判断是否是当前操作需要填充的字段
                if (tableField.fill() == fillType || tableField.fill() == FieldFill.INSERT_UPDATE) {
                    // 设置字段值
                    setFieldValByName(field.getName(), value, metaObject);
                }
            }
        }
    }
}
