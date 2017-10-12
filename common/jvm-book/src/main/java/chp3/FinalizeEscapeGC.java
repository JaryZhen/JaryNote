package chp3;

/**
 * �˴�����ʾ�����㣺
 * 1.��������ڱ�GCʱ�������ȡ�
 * 2.�����ԾȵĻ���ֻ��һ�Σ���Ϊһ�������finalize()�������ֻ�ᱻϵͳ�Զ�����һ��
 * @author zzm
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, i am still alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize mehtod executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new FinalizeEscapeGC();

        //�����һ�γɹ������Լ�
        SAVE_HOOK = null;
        System.gc();
        // ��ΪFinalizer�������ȼ��ܵͣ���ͣ0.5�룬�Եȴ���
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }

        // ������δ������������ȫ��ͬ����������Ծ�ȴʧ����
        SAVE_HOOK = null;
        System.gc();
        // ��ΪFinalizer�������ȼ��ܵͣ���ͣ0.5�룬�Եȴ���
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }
    }
}

