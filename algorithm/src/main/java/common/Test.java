package common;

/**
 * Created by xmr on 2018/3/28.
 */
public class Test {

    public static void main(String[] args) {
        TN yz4 = new TN(4, null, null);
        TN yz5 = new TN(5, null, null);

        TN left2 = new TN(2, yz4, yz5);

        TN yz6 = new TN(6, null, null);
        TN rigt3 = new TN(3, yz6, null);
        TN root = new TN(1, left2, rigt3);


        new Test().fun(root, "");
    }

    static class TN {
        public TN(int data, TN left, TN right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        int data;
        TN left, right;
    }

    public String fun(TN tn, String str) {
        String data = tn.data + str;
        if (tn.left != null) {
            System.out.println(fun(tn.left, data));
        }
        if (tn.right != null) {
            System.out.println(fun(tn.right, data));
        }
        if (tn.left == null && tn.right == null)
            return data;

        return "";
    }
}
