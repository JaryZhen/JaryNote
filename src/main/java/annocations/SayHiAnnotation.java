package annocations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by root on 5/15/17.
 */
@Retention(RetentionPolicy.RUNTIME) // ��ʾע��������ʱ��Ȼ����
@Target(ElementType.METHOD)
public @interface SayHiAnnotation {
    String paramValue() default "jary";//��ʾ�ҵ�ע����Ҫһ������ ��Ϊ"paramValue" Ĭ��ֵΪ"jary"
}
