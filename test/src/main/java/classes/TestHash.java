package classes;

/**
 * Created by Jary on 2017/8/30 0030.
 */
public class TestHash {
    int age;

    @Override
    public boolean equals(Object obj) {
        //按照你想要的方法去比较，比如我这里比较的是年龄，年龄相等就返回true
        if (!(obj instanceof TestHash))
            return false;
        TestHash p = (TestHash) obj;
        return this.age == p.age && p.hashCode() == this.hashCode()? true : false;
    }

  /*  @Override
    public int hashCode() {
        return super.hashCode();
    }*/

    public static void main(String[] args) {
        TestHash p1 = new TestHash();
        TestHash p2 = new TestHash();
        p1.age = 1;
        p2.age = 1;
        p1.toString();
        int hc = System.identityHashCode(p1);
        System.out.println(hc);//如果没有重写equals方法，Object默认是比较他们的引用，所以返回的是false，你可以试试

    }
}
