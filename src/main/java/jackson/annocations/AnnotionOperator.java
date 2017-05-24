package jackson.annocations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotionOperator {
    public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        SayHiEmlement element = new SayHiEmlement(); // ��ʼ��һ��ʵ�������ڷ�������
        Method[] methods = SayHiEmlement.class.getDeclaredMethods(); // ������з���

        for (Method method : methods) {
            SayHiAnnotation annotationTmp = null;
            if ((annotationTmp = method.getAnnotation(SayHiAnnotation.class)) != null) {
                // ����Ƿ�ʹ�������ǵ�ע��
                System.out.println("yes");
                method.invoke(element, annotationTmp.paramValue(), annotationTmp.intV()); // ���ʹ�������ǵ�ע�⣬���ǾͰ�ע�����"paramValue"����ֵ��Ϊ�������������÷���
            } else
                method.invoke(element, "Rose", 44); // ���û��ʹ�����ǵ�ע�⣬���Ǿ���Ҫʹ����ͨ�ķ�ʽ�����÷�����
        }
    }
}