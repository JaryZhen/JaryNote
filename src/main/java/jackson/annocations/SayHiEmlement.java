package jackson.annocations;

/**
 * Created by root on 5/15/17.
 */
public class SayHiEmlement {
    // ��ͨ�ķ���
    public  void SayHiDefault(String name,int a ) {
        System.out.println("Hi, " + name+ " im " +a);
    }

    // ʹ��ע�Ⲣ��������ķ���
    @SayHiAnnotation(paramValue = "Jack",intV =12)
    public  void SayHiAnnotation(String name,int a) {
        System.out.println("Hi, " + name + " im " +a);
    }

    // ʹ��ע�Ⲣʹ��Ĭ�ϲ����ķ���
    @SayHiAnnotation(intV = 20)
    public  void SayHiAnnotationDefault(String name,int a) {
        System.out.println("Hi, " + name+ " im " +a);
    }
}
