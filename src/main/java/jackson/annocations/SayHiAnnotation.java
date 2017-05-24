package jackson.annocations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by root on 5/15/17.
 */
@Retention(RetentionPolicy.RUNTIME) // ��ʾע��������ʱ��Ȼ����
@Target(ElementType.METHOD)
/*
@Target ��ʾ��ע������ʲô�ط������ܵ� ElemenetType ����������
         ElemenetType.CONSTRUCTOR ����������
         ElemenetType.FIELD ������������ enum ʵ����
         ElemenetType.LOCAL_VARIABLE �ֲ���������
         ElemenetType.METHOD ��������
         ElemenetType.PACKAGE ������
         ElemenetType.PARAMETER ��������
         ElemenetType.TYPE �࣬�ӿڣ�����ע�����ͣ���enum����
 */
public @interface SayHiAnnotation {
    String paramValue() default "jary";//��ʾ�ҵ�ע����Ҫһ������ ��Ϊ"paramValue" Ĭ��ֵΪ"jary"
    int intV ();
}
