package com.chards.committee.util;

import java.lang.annotation.*;

/**
 * 数据权限自定义注解
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {

    // 对学生状态的筛选控制（例如，在绝大多数情况下，只会访问在读的同学的信息）
    String studentState() default "在读";

    // 是否要进行数据权限控制（例如限制计算机学院辅导员只能访问计算机学院同学的信息）
    String  isDataAccessControlled() default "是";

}