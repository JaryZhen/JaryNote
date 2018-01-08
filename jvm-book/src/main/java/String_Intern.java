/**
 * Created by Jary on 2017/10/11 0011.
 */
public class String_Intern {
    public static void main(String[] args) {

        /*
        1.采用new 创建的字符串对象不进入字符串池，
        2.字符串相加的时候，都是静态字符串的结果会添加到字符串池，
        3.如果其中含有变量（如f中的e）则不会进入字符串池中。
         */
/*        String str1 = "a";
        String str2 = "b";
        String str3 = "ab";
        String str4 = str1 + str2;
        String str5 = new String("ab");

        System.out.println(str5.equals(str3));
        System.out.println(str5 == str3);
        System.out.println(str5.intern() == str3);
        System.out.println(str5.intern() == str4);
        */

/*        String a = new String("ab");
        System.out.println(a.intern());//t

        String b = new String("ab");
        String c = "ab";
        String d = "a" + "b";
        String e = "b";
        String f = "a" + e;
        System.out.println(a==b);//f
        System.out.println(a.intern() == c);//t
        System.out.println(b.intern() == d);//f
        System.out.println(b.intern() == f);//f
        System.out.println(b.intern() == a.intern());//t
        */


        String a = "a";
        String b = "abc";
        String c = "a"+ "b" + "c";
        String d = "a" + "bc";
        String e = "ab" + "c";

        System.out.println(b == c);
        System.out.println(a == e);
        System.out.println(c == d);
        System.out.println(c == e);
    }
}
