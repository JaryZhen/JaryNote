package chp4;

/**
 * Created by Jary on 2017/10/13 0013.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * �ڴ�ռλ������һ��OOMObject��Լռ64K
 */
public class OOMObject {
    public byte[] placeholder = new byte[64 * 1024];


    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            // ������ʱ����������ߵı仯��������
            Thread.sleep(50);
            list.add(new OOMObject());
        }

        System.gc();
        Thread.sleep(50000000);
    }

    public static void main(String[] args) throws Exception {
        fillHeap(1000);
    }
}