package annotation;

import java.lang.annotation.*;

/**
 * Created by Jary on 2018/1/19 0019.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName {
    String value() default "";
}

