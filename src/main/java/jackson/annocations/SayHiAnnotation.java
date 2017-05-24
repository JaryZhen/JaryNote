package jackson.annocations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by root on 5/15/17.
 */
@Retention(RetentionPolicy.RUNTIME) // 表示注解在运行时依然存在
@Target(ElementType.METHOD)
/*
@Target 表示该注解用于什么地方，可能的 ElemenetType 参数包括：
         ElemenetType.CONSTRUCTOR 构造器声明
         ElemenetType.FIELD 域声明（包括 enum 实例）
         ElemenetType.LOCAL_VARIABLE 局部变量声明
         ElemenetType.METHOD 方法声明
         ElemenetType.PACKAGE 包声明
         ElemenetType.PARAMETER 参数声明
         ElemenetType.TYPE 类，接口（包括注解类型）或enum声明
 */
public @interface SayHiAnnotation {
    String paramValue() default "jary";//表示我的注解需要一个参数 名为"paramValue" 默认值为"jary"
    int intV ();
}
