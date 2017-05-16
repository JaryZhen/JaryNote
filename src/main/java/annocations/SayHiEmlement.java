package annocations;

/**
 * Created by root on 5/15/17.
 */
public class SayHiEmlement {
    // ��ͨ�ķ���
    public static void SayHiDefault(String name) {
        System.out.println("Hi, " + name);
    }

    // ʹ��ע�Ⲣ��������ķ���
    @SayHiAnnotation(paramValue = "Jack")
    public static void SayHiAnnotation(String name) {
        System.out.println("Hi, " + name);
    }

    // ʹ��ע�Ⲣʹ��Ĭ�ϲ����ķ���
    @SayHiAnnotation
    public static void SayHiAnnotationDefault(String name) {
        System.out.println("Hi, " + name);
    }

    public static void main(String[] args) {
        SayHiEmlement.SayHiAnnotation("dasd");
    }
}
