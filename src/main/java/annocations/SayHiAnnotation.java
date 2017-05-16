package annocations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by root on 5/15/17.
 */
@Retention(RetentionPolicy.RUNTIME) // 表示注解在运行时依然存在
@Target(ElementType.METHOD)
public @interface SayHiAnnotation {
    String paramValue() default "jary";//表示我的注解需要一个参数 名为"paramValue" 默认值为"jary"
}
